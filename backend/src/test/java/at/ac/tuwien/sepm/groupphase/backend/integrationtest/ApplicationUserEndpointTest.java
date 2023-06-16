package at.ac.tuwien.sepm.groupphase.backend.integrationtest;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.UserCreateDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.UserDetailDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.UserLoginDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.UserMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.ApplicationUser;
import at.ac.tuwien.sepm.groupphase.backend.repository.ApplicationUserRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.xml.bind.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class ApplicationUserEndpointTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    ApplicationUserRepository applicationUserRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    PasswordEncoder passwordEncoder;


    static final String BASE_PATH = "/api/v1/user";

    //Initial Setup for Tests
    @BeforeEach
    @Transactional
    public void beforeEach() throws ValidationException {

        // Delete existing users
        applicationUserRepository.deleteAll();

        UserCreateDto unblockedUser = new UserCreateDto(-1000L, "John@email.com", "John", "Doe", LocalDate.parse("1988-12-12"),
            "Teststreet 44/7", 1010L, "Vienna", "Password123%", false, false);

        UserCreateDto blockedUser = new UserCreateDto(-1000L, "James@email.com", "James", "Doe", LocalDate.parse("1988-12-12"),
            "Teststreet 44/7", 1010L, "Vienna", "Password123%", false, true);

        userService.register(userMapper.userCreateDtoToEntity(unblockedUser));
        userService.register(userMapper.userCreateDtoToEntity(blockedUser));
    }


    @Test
    @Transactional
    public void givenUserDetailDtoAndAuthorizationToken_whenUpdateUser_thenUpdateUser() throws Exception {
        UserDetailDto userDetailDto = new UserDetailDto(
            1L, false, "John@email.com", "Jonathan", "Frakes", LocalDate.parse("1988-12-12"),
            "Blumenstraße 1-2/44", 10000L, "Vienna", "Password123%", false
        );
        //Receive the token for the user before performing edit
        UserLoginDto userLoginDto = new UserLoginDto();
        userLoginDto.setEmail("John@email.com");
        userLoginDto.setPassword("Password123%");
        String token = userService.login(userLoginDto);

        MvcResult mvcResult = this.mockMvc.perform(put(BASE_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, token)
                .content(objectMapper.writeValueAsString(userDetailDto)))
            .andExpect(status().isOk())
            .andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();

        assertAll(
            () -> assertEquals(HttpStatus.OK.value(), response.getStatus())
        );
    }

    @Test
    @Transactional
    public void givenUserDetailDtoAndAuthorizationTokenButWrongPassword_whenUpdateUser_isForbidden() throws Exception {
        UserDetailDto userDetailDto = new UserDetailDto(
            1L, false, "John@email.com", "Jonathan", "Frakes", LocalDate.parse("1988-12-12"),
            "Blumenstraße 1-2/44", 10000L, "Vienna", "ThisIsAWrongPassword123%", false
        );
        //Receive the token for the user before performing edit
        UserLoginDto userLoginDto = new UserLoginDto();
        userLoginDto.setEmail("John@email.com");
        userLoginDto.setPassword("Password123%");
        String token = userService.login(userLoginDto);

        mockMvc.perform(put(BASE_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, token)
                .content(objectMapper.writeValueAsString(userDetailDto)))
            .andExpect(status().isForbidden());
    }


    @Test
    @Transactional
    public void givenUserDetailDtoWithWrongFirstNameAndAuthorizationToken_Then_ValidationException() throws Exception {
        UserDetailDto userDetailDto = new UserDetailDto(
            1L, false, "John@email.com", "Jonathan123", "Frakes", LocalDate.parse("1988-12-12"),
            "Blumenstraße 1-2/44", 10000L, "Vienna", "Password123%", false
        );
        //Receive the token for the user before performing edit
        UserLoginDto userLoginDto = new UserLoginDto();
        userLoginDto.setEmail("John@email.com");
        userLoginDto.setPassword("Password123%");
        String token = userService.login(userLoginDto);

        mockMvc.perform(put(BASE_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, token)
                .content(objectMapper.writeValueAsString(userDetailDto)))
            .andExpect(status().isUnprocessableEntity())
            .andExpect(jsonPath("$.errors[0].message").value("First name must contain only letters"));
    }

    @Test
    @Transactional
    public void givenUserDetailDtoWithWrongLastNameAndAuthorizationToken_Then_ValidationException() throws Exception {
        UserDetailDto userDetailDto = new UserDetailDto(
            1L, false, "John@email.com", "Jonathan", "Frakes123", LocalDate.parse("1988-12-12"),
            "Blumenstraße 1-2/44", 10000L, "Vienna", "Password123%", false
        );
        //Receive the token for the user before performing edit
        UserLoginDto userLoginDto = new UserLoginDto();
        userLoginDto.setEmail("John@email.com");
        userLoginDto.setPassword("Password123%");
        String token = userService.login(userLoginDto);

        mockMvc.perform(put(BASE_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, token)
                .content(objectMapper.writeValueAsString(userDetailDto)))
            .andExpect(status().isUnprocessableEntity())
            .andExpect(jsonPath("$.errors[0].message").value("Last name must contain only letters"));
    }

    @Test
    @Transactional
    public void givenUserDetailDtoWithWrongAreaCodeAndAuthorizationToken_Then_ValidationException() throws Exception {
        UserDetailDto userDetailDto = new UserDetailDto(
            1L, false, "John@email.com", "Jonathan", "Frakes", LocalDate.parse("1988-12-12"),
            "Blumenstraße 1-2/44", -10000L, "Vienna", "Password123%", false
        );
        //Receive the token for the user before performing edit
        UserLoginDto userLoginDto = new UserLoginDto();
        userLoginDto.setEmail("John@email.com");
        userLoginDto.setPassword("Password123%");
        String token = userService.login(userLoginDto);

        mockMvc.perform(put(BASE_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, token)
                .content(objectMapper.writeValueAsString(userDetailDto)))
            .andExpect(status().isUnprocessableEntity())
            .andExpect(jsonPath("$.errors[0].message").value("Area Code must be a positive number"));
    }

    @Test
    @Transactional
    public void givenUserDetailDtoWithWrongCityNameAndAuthorizationToken_Then_ValidationException() throws Exception {
        UserDetailDto userDetailDto = new UserDetailDto(
            1L, false, "John@email.com", "Jonathan", "Frakes", LocalDate.parse("1988-12-12"),
            "Blumenstraße 1-2/44", 10000L, "Vienna123", "Password123%", false
        );
        //Receive the token for the user before performing edit
        UserLoginDto userLoginDto = new UserLoginDto();
        userLoginDto.setEmail("John@email.com");
        userLoginDto.setPassword("Password123%");
        String token = userService.login(userLoginDto);

        mockMvc.perform(put(BASE_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, token)
                .content(objectMapper.writeValueAsString(userDetailDto)))
            .andExpect(status().isUnprocessableEntity())
            .andExpect(jsonPath("$.errors[0].message").value("City name must contain only letters"));
    }

    @Test
    @Transactional
    public void givenUserDetailDtoWithBirthdateInTheFutureAndAuthorizationToken_Then_ValidationException() throws Exception {
        UserDetailDto userDetailDto = new UserDetailDto(
            1L, false, "John@email.com", "Jonathan", "Frakes", LocalDate.parse("2030-12-12"),
            "Blumenstraße 1-2/44", 10000L, "Vienna", "Password123%", false
        );
        //Receive the token for the user before performing edit
        UserLoginDto userLoginDto = new UserLoginDto();
        userLoginDto.setEmail("John@email.com");
        userLoginDto.setPassword("Password123%");
        String token = userService.login(userLoginDto);

        mockMvc.perform(put(BASE_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, token)
                .content(objectMapper.writeValueAsString(userDetailDto)))
            .andExpect(status().isUnprocessableEntity())
            .andExpect(jsonPath("$.errors[0].message").value("Birthdate must be in the past"));
    }
    private final ApplicationUser applicationUser =
        new ApplicationUser("marty@email.com", "Martin", "Gerdenich", LocalDate.parse("1999-12-12"), "Teststraße", 1010L, "Vienna", "Password123%", false,
            false);
}
