package at.ac.tuwien.sepm.groupphase.backend.unittests;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.news.AbbreviatedNewsDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.news.DetailedNewsDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.news.NewsInquiryDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.NewsMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.News;
import at.ac.tuwien.sepm.groupphase.backend.entity.NewsImage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class NewsMappingTest implements TestData {

    private static final News news = News.NewsBuilder.aNews()
        .withId(ID)
        .withTitle(TEST_NEWS_TITLE)
        .withShortText(TEST_NEWS_SUMMARY)
        .withFullText(TEST_NEWS_TEXT)
        .withCreatedAt(TEST_NEWS_PUBLISHED_AT)
        .withCoverImage(TEST_COVER_IMAGE)
        .build();

    private static final NewsInquiryDto newsInquiryDto = NewsInquiryDto.NewsInquiryDtoBuilder.aNewsInquiryDto()
        .withTitle(TEST_NEWS_TITLE)
        .withShortText(TEST_NEWS_SUMMARY)
        .withFullText(TEST_NEWS_TEXT)
        .withCoverImage(TEST_COVER_IMAGE)
        .withImages(TEST_NEWS_IMAGE_DATA_LIST)
        .build();

    @Autowired
    private NewsMapper newsMapper;

    @BeforeAll
    public static void setImagesForNews() {
        NewsImage img1 = NewsImage.NewsImageBuilder.aNewsImage().withNews(news).withImageData(TEST_NEWS_IMAGE_DATA_LIST.get(0)).build();
        NewsImage img2 = NewsImage.NewsImageBuilder.aNewsImage().withNews(news).withImageData(TEST_NEWS_IMAGE_DATA_LIST.get(1)).build();
        NewsImage img3 = NewsImage.NewsImageBuilder.aNewsImage().withNews(news).withImageData(TEST_NEWS_IMAGE_DATA_LIST.get(2)).build();
        List<NewsImage> testImageList = new LinkedList<>(Arrays.asList(img1,img2,img3));

        news.setImages(testImageList);
    }

    @Test
    public void givenNothing_whenMapNewsToDetailedNewsDto_thenDtoHasAllPropertiesExceptAbbreviatedText() {
        DetailedNewsDto detailedNewsDto = newsMapper.newsToDetailedNewsDto(news);
        assertAll(
            () -> assertEquals(ID, detailedNewsDto.getId()),
            () -> assertEquals(TEST_NEWS_TITLE, detailedNewsDto.getTitle()),
            () -> assertEquals(TEST_NEWS_TEXT, detailedNewsDto.getFullText()),
            () -> assertEquals(TEST_NEWS_PUBLISHED_AT, detailedNewsDto.getCreatedAt()),
            () -> assertEquals(TEST_COVER_IMAGE, detailedNewsDto.getCoverImage()),
            () -> assertEquals(3, detailedNewsDto.getImages().size()),
            () -> assertEquals(TEST_NEWS_IMAGE_DATA_LIST, detailedNewsDto.getImages())
        );
    }

    @Test
    public void givenNothing_whenMapListWithTwoNewsEntitiesToAbbreviatedNewsDto_thenGetDtoListWithSizeTwoAndAllPropertiesExceptFullTextAndImages() {
        List<News> newsList = new ArrayList<>();
        newsList.add(news);
        newsList.add(news);

        List<AbbreviatedNewsDto> abbreviatedNewsDtos = newsMapper.newsToAbbreviatedNewsDto(newsList);
        assertEquals(2, abbreviatedNewsDtos.size());
        AbbreviatedNewsDto abbreviatedNewsDto = abbreviatedNewsDtos.get(0);
        assertAll(
            () -> assertEquals(ID, abbreviatedNewsDto.getId()),
            () -> assertEquals(TEST_NEWS_TITLE, abbreviatedNewsDto.getTitle()),
            () -> assertEquals(TEST_NEWS_SUMMARY, abbreviatedNewsDto.getShortText()),
            () -> assertEquals(TEST_NEWS_PUBLISHED_AT, abbreviatedNewsDto.getCreatedAt()),
            () -> assertEquals(TEST_COVER_IMAGE, abbreviatedNewsDto.getCoverImage())
        );
    }

    @Test
    public void givenNothing_whenMapNewsInquiryDtoToNewsEntity_thenEntityHasAllPropertiesExceptIdAndCreatedAtAndImagesAreStoredInNewsImageEntities() {
        News newsEntity = newsMapper.newsInquiryDtoWithImagesToNews(newsInquiryDto);

        // entity not yet persisted in db
        assertAll(
            () -> assertNull(newsEntity.getId()),
            () -> assertEquals(TEST_NEWS_TITLE, newsEntity.getTitle()),
            () -> assertEquals(TEST_NEWS_TEXT, newsEntity.getFullText()),
            () -> assertNull(newsEntity.getCreatedAt()),
            () -> assertEquals(TEST_COVER_IMAGE, newsEntity.getCoverImage()),
            () -> assertEquals(3, newsEntity.getImages().size()),
            () -> assertEquals(TEST_NEWS_IMAGE_DATA_LIST, newsEntity.getImages().stream().map(NewsImage::getImageData).toList())
        );
    }


}
