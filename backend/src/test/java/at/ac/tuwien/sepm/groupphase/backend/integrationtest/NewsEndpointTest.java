package at.ac.tuwien.sepm.groupphase.backend.integrationtest;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.config.properties.SecurityProperties;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.news.DetailedNewsDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.news.NewsInquiryDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.NewsMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.News;
import at.ac.tuwien.sepm.groupphase.backend.entity.NewsImage;
import at.ac.tuwien.sepm.groupphase.backend.repository.NewsImageRepository;
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

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class NewsEndpointTest implements TestData {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private NewsImageRepository newsImageRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private NewsMapper newsMapper;

    @Autowired
    private JwtTokenizer jwtTokenizer;

    @Autowired
    private SecurityProperties securityProperties;

    private News news;

    @BeforeEach
    public void beforeEach() {
        newsRepository.deleteAll();
        newsImageRepository.deleteAll();
        news = News.NewsBuilder.aNews()
            .withTitle(TEST_NEWS_TITLE)
            .withShortText(TEST_NEWS_SUMMARY)
            .withFullText(TEST_NEWS_TEXT)
            .withCreatedAt(TEST_NEWS_PUBLISHED_AT)
            .withCoverImage(TEST_COVER_IMAGE)
            .build();

        NewsImage img1 = NewsImage.NewsImageBuilder.aNewsImage().withNews(news).withImageData(TEST_NEWS_IMAGE_DATA_LIST.get(0)).build();
        NewsImage img2 = NewsImage.NewsImageBuilder.aNewsImage().withNews(news).withImageData(TEST_NEWS_IMAGE_DATA_LIST.get(1)).build();
        NewsImage img3 = NewsImage.NewsImageBuilder.aNewsImage().withNews(news).withImageData(TEST_NEWS_IMAGE_DATA_LIST.get(2)).build();
        List<NewsImage> testImageList = new LinkedList<>(Arrays.asList(img1,img2,img3));

        news.setImages(testImageList);
    }

    /* // TODO: (not implemented yet, method not allowed for now)
    @Test
    public void givenNothing_whenFindAll_thenEmptyList() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get(NEWS_BASE_URI)
                .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(ADMIN_USER, ADMIN_ROLES)))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        // response in json format:
        assertEquals(MediaType.APPLICATION_JSON_VALUE, response.getContentType());

        // map json string to list:
        List<AbbreviatedNewsDto> abbreviatedNewsDtos = Arrays.asList(objectMapper.readValue(response.getContentAsString(),
            AbbreviatedNewsDto[].class));

        assertEquals(0, abbreviatedNewsDtos.size());
    }

    @Test
    public void givenOneNews_whenFindAll_thenListWithSizeOneAndNewsWithAllPropertiesExceptFullTextAndImages()
        throws Exception {
        newsRepository.save(news);

        MvcResult mvcResult = this.mockMvc.perform(get(NEWS_BASE_URI)
                .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(ADMIN_USER, ADMIN_ROLES)))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(MediaType.APPLICATION_JSON_VALUE, response.getContentType());

        List<AbbreviatedNewsDto> abbreviatedNewsDtos = Arrays.asList(objectMapper.readValue(response.getContentAsString(),
            AbbreviatedNewsDto[].class));

        assertEquals(1, abbreviatedNewsDtos.size());
        AbbreviatedNewsDto abbreviatedNewsDto = abbreviatedNewsDtos.get(0);
        assertAll(
            () -> assertEquals(news.getId(), abbreviatedNewsDto.getId()),
            () -> assertEquals(TEST_NEWS_TITLE, abbreviatedNewsDto.getTitle()),
            () -> assertEquals(TEST_NEWS_SUMMARY, abbreviatedNewsDto.getShortText()),
            () -> assertEquals(TEST_NEWS_PUBLISHED_AT, abbreviatedNewsDto.getCreatedAt()),
            () -> assertEquals(TEST_COVER_IMAGE, abbreviatedNewsDto.getCoverImage()),
            () -> assertEquals(TEST_NEWS_IMAGE_DATA_LIST, newsImageRepository.findAll().stream().map(NewsImage::getImageData).toList())
        );
    }

     */

    /* // TODO (not implemented yet)

    @Test
    public void givenOneNews_whenFindById_thenNewsWithAllPropertiesExceptShortText() throws Exception {
        newsRepository.save(news);

        MvcResult mvcResult = this.mockMvc.perform(get(NEWS_BASE_URI + "/{id}", news.getId())
                .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(ADMIN_USER, ADMIN_ROLES)))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        assertAll(
            () -> assertEquals(HttpStatus.OK.value(), response.getStatus()),
            () -> assertEquals(MediaType.APPLICATION_JSON_VALUE, response.getContentType())
        );

        DetailedNewsDto detailedNewsDto = objectMapper.readValue(response.getContentAsString(),
            DetailedNewsDto.class);

        assertAll(
            () -> assertEquals(detailedNewsDto, newsMapper.newsToDetailedNewsDto(news)),
            () -> assertEquals(detailedNewsDto.getImages(), news.getImages().stream().map(NewsImage::getImageData).toList())
        );
    }

    @Test
    public void givenOneNews_whenFindByNonExistingId_then404() throws Exception {
        newsRepository.save(news);

        MvcResult mvcResult = this.mockMvc.perform(get(MESSAGE_BASE_URI + "/{id}", -1)
                .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(ADMIN_USER, ADMIN_ROLES)))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
    }

    */

    // TODO: Test getById and delete

    @Test
    public void givenNothing_whenPost_thenNewsWithAllSetPropertiesPlusIdAndPublishedDate() throws Exception {
        news.setCreatedAt(null);
        NewsInquiryDto newsInquiryDto = newsMapper.newsToNewsInquiryDto(news);
        String body = objectMapper.writeValueAsString(newsInquiryDto);

        MvcResult mvcResult = this.mockMvc.perform(post(NEWS_BASE_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
                .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(ADMIN_USER, ADMIN_ROLES)))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
        assertEquals(MediaType.APPLICATION_JSON_VALUE, response.getContentType());

        DetailedNewsDto detailedNewsDto = objectMapper.readValue(response.getContentAsString(),
            DetailedNewsDto.class);

        assertNotNull(detailedNewsDto.getId());
        assertNotNull(detailedNewsDto.getCreatedAt());
        assertTrue(isNow(detailedNewsDto.getCreatedAt()));
        //Set generated properties to null to make the response comparable with the original input
        detailedNewsDto.setId(null);
        detailedNewsDto.setCreatedAt(null);

        assertAll(
            () -> assertEquals(detailedNewsDto, newsMapper.newsToDetailedNewsDto(news)),
            () -> assertEquals(detailedNewsDto.getImages(), news.getImages().stream().map(NewsImage::getImageData).toList())
        );
    }

    @Test
    public void givenNothing_whenPostNewsAndTitleBlank_then400() throws Exception {
        news.setTitle("        ");
        NewsInquiryDto newsInquiryDto = newsMapper.newsToNewsInquiryDto(news);
        String body = objectMapper.writeValueAsString(newsInquiryDto);

        MvcResult mvcResult = this.mockMvc.perform(post(NEWS_BASE_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
                .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(ADMIN_USER, ADMIN_ROLES)))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        assertAll(
            () -> assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus()),
            () -> {
                //Reads the errors from the body
                String content = response.getContentAsString();
                content = content.substring(content.indexOf('[') + 1, content.indexOf(']'));
                String[] errors = content.split(",");
                // check if title is not null and not blank => 4 errors
                assertEquals(1, errors.length);
            }
        );
    }

    @Test
    public void givenNothing_whenPostInvalid_then400() throws Exception {
        news.setTitle(null);
        news.setShortText(null);
        news.setFullText(null);
        NewsInquiryDto newsInquiryDto = newsMapper.newsToNewsInquiryDto(news);
        String body = objectMapper.writeValueAsString(newsInquiryDto);

        MvcResult mvcResult = this.mockMvc.perform(post(NEWS_BASE_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
                .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(ADMIN_USER, ADMIN_ROLES)))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        assertAll(
            () -> assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus()),
            () -> {
                //Reads the errors from the body
                String content = response.getContentAsString();
                content = content.substring(content.indexOf('[') + 1, content.indexOf(']'));
                String[] errors = content.split(",");
                // check if title is not null and not blank => 4 errors
                assertEquals(4, errors.length);
            }
        );
    }

    @Test
    public void givenNothing_whenPostStringsTooLong_then400() throws Exception {
        news.setTitle("a".repeat(51));
        news.setShortText("a".repeat(101));
        news.setFullText("a".repeat(10001));
        NewsInquiryDto newsInquiryDto = newsMapper.newsToNewsInquiryDto(news);
        String body = objectMapper.writeValueAsString(newsInquiryDto);

        MvcResult mvcResult = this.mockMvc.perform(post(NEWS_BASE_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
                .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(ADMIN_USER, ADMIN_ROLES)))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        assertAll(
            () -> assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus()),
            () -> {
                //Reads the errors from the body
                String content = response.getContentAsString();
                content = content.substring(content.indexOf('[') + 1, content.indexOf(']'));
                String[] errors = content.split(",");
                assertEquals(3, errors.length);
            }
        );
    }

    // bad if test is run between two hours
    private boolean isNow(LocalDateTime date) {
        LocalDateTime today = LocalDateTime.now();
        return date.getYear() == today.getYear() && date.getDayOfYear() == today.getDayOfYear() &&
            date.getHour() == today.getHour();
    }

}
