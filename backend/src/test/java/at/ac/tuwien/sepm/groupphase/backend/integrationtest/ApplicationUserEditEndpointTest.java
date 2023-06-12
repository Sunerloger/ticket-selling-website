package at.ac.tuwien.sepm.groupphase.backend.integrationtest;

import at.ac.tuwien.sepm.groupphase.backend.config.properties.SecurityProperties;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.ApplicationUserEditEndpoint;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.RegisterEndpoint;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.UserDeleteDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.UserRegisterDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.news.AbbreviatedNewsDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.UserMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.ApplicationUser;
import at.ac.tuwien.sepm.groupphase.backend.entity.News;
import at.ac.tuwien.sepm.groupphase.backend.repository.ApplicationUserRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.NewsRepository;
import at.ac.tuwien.sepm.groupphase.backend.security.JwtTokenizer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static at.ac.tuwien.sepm.groupphase.backend.basetest.TestData.ADMIN_ROLES;
import static at.ac.tuwien.sepm.groupphase.backend.basetest.TestData.ADMIN_USER;
import static at.ac.tuwien.sepm.groupphase.backend.basetest.TestData.DEFAULT_USER;
import static at.ac.tuwien.sepm.groupphase.backend.basetest.TestData.NEWS_BASE_URI;
import static at.ac.tuwien.sepm.groupphase.backend.basetest.TestData.TEST_NEWS_SUMMARY;
import static at.ac.tuwien.sepm.groupphase.backend.basetest.TestData.TEST_NEWS_TITLE;
import static at.ac.tuwien.sepm.groupphase.backend.basetest.TestData.USER_ROLES;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class ApplicationUserEditEndpointTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ApplicationUserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private JwtTokenizer jwtTokenizer;

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private ObjectMapper objectMapper;

    static final String BASE_PATH = ApplicationUserEditEndpoint.BASE_PATH;
    static final String POST_PATH = RegisterEndpoint.BASE_PATH;

    @BeforeEach
    public void beforeEach() {
        userRepository.deleteAll();
    }

    @Test
    public void givenApplicationUsersWithNewsRead_whenDelete_thenGetAllLengthIs0And200() throws Exception {

        ApplicationUser user = new ApplicationUser(DEFAULT_USER, "Martin",
            "Gerdenich", LocalDate.parse("1999-12-12"), "Teststraße", 1010L,
            "Vienna", "123", false, false);

        UserRegisterDto userRegisterDto = userMapper.entityToDto(user);
        String body = objectMapper.writeValueAsString(userRegisterDto);

        MvcResult mvcResultPost = this.mockMvc.perform(post(POST_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
            .andDo(print())
            .andReturn();

        MockHttpServletResponse responsePost = mvcResultPost.getResponse();

        // List<ApplicationUser> userList = userRepository.findAll();

        assertAll(
            () -> assertEquals(HttpStatus.CREATED.value(), responsePost.getStatus()),
            () -> assertEquals(userRepository.findAll().size(),1)
        );

        ApplicationUser admin = new ApplicationUser();
        admin.setEmail(ADMIN_USER);
        admin.setAdmin(true);

        userRepository.save(admin);

        assertEquals(userRepository.findAll().size(),2);

        News news = News.NewsBuilder.aNews()
            .withTitle(TEST_NEWS_TITLE)
            .withShortText(TEST_NEWS_SUMMARY)
            .withFullText("")
            .build();

        newsRepository.save(news);

        assertEquals(newsRepository.findAll().size(),1);


        MvcResult mvcResultPut1 = this.mockMvc.perform(put(NEWS_BASE_URI + "/" + news.getId())
                .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(ADMIN_USER, ADMIN_ROLES)))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse responsePut1 = mvcResultPut1.getResponse();

        assertEquals(HttpStatus.CREATED.value(), responsePut1.getStatus());

        MvcResult mvcResultPut2 = this.mockMvc.perform(put(NEWS_BASE_URI + "/" + news.getId())
                .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(DEFAULT_USER, USER_ROLES)))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse responsePut2 = mvcResultPut2.getResponse();

        assertEquals(HttpStatus.CREATED.value(), responsePut2.getStatus());

        UserDeleteDto userDeleteDto = new UserDeleteDto(userRepository.findUserByEmail(DEFAULT_USER).getId(),
            DEFAULT_USER, "123");

        MvcResult mvcResult = this.mockMvc.perform(delete(BASE_PATH)
                .param("id", userDeleteDto.id().toString())
                .param("email", userDeleteDto.email())
                .param("password", userDeleteDto.password())
                .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(ADMIN_USER, ADMIN_ROLES)))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());


        // default pageIndex is 0
        mvcResult = this.mockMvc.perform(get(NEWS_BASE_URI)
                .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(ADMIN_USER, ADMIN_ROLES))
                .param("loadAlreadyRead","true"))
            .andDo(print())
            .andReturn();
        response = mvcResult.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        // response in json format:
        assertEquals(MediaType.APPLICATION_JSON_VALUE, response.getContentType());

        // map json string to list:
        List<AbbreviatedNewsDto> abbreviatedNewsDtos = Arrays.asList(objectMapper.readValue(response.getContentAsString(),
            AbbreviatedNewsDto[].class));

        assertEquals(1, abbreviatedNewsDtos.size());


        // default pageIndex is 0
        mvcResult = this.mockMvc.perform(get(NEWS_BASE_URI)
                .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(DEFAULT_USER, USER_ROLES))
                .param("loadAlreadyRead","false"))
            .andDo(print())
            .andReturn();
        response = mvcResult.getResponse();

        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
    }
}
