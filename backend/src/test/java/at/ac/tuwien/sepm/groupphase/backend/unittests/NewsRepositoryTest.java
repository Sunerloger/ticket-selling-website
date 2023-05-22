package at.ac.tuwien.sepm.groupphase.backend.unittests;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.entity.News;
import at.ac.tuwien.sepm.groupphase.backend.entity.NewsImage;
import at.ac.tuwien.sepm.groupphase.backend.repository.NewsImageRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.NewsRepository;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
// This test slice annotation is used instead of @SpringBootTest to load only repository beans instead of
// the entire application context
@DataJpaTest
@ActiveProfiles("test")
public class NewsRepositoryTest implements TestData {

    @Autowired
    private NewsRepository newsRepository;
    @Autowired
    private NewsImageRepository newsImageRepository;

    @BeforeEach
    public void clearDatabase() {
        newsRepository.deleteAll();
        newsImageRepository.deleteAll();
    }

    @Test
    public void givenNothing_whenSaveNewsWithoutImages_thenFindListWithOneElementAndFindNewsByIdAndFindNoImagesInNewsImageRepository() {
        News news = News.NewsBuilder.aNews()
            .withTitle(TEST_NEWS_TITLE)
            .withShortText(TEST_NEWS_SUMMARY)
            .withFullText(TEST_NEWS_TEXT)
            // cannot be null:
            .withImages(new LinkedList<>())
            .build();

        newsRepository.save(news);

        // test if news present
        assertAll(
            () -> assertEquals(1, newsRepository.findAll().size()),
            () -> assertEquals(0, newsImageRepository.findAll().size()),
            () -> assertNotNull(newsRepository.findById(news.getId())),

        // test properties of news if present
            () -> assertNotNull(newsRepository.findById(news.getId()).get().getCreatedAt()),
            () -> assertNull(newsRepository.findById(news.getId()).get().getCoverImage()),
            () -> assertTrue(newsRepository.findById(news.getId()).get().getImages().isEmpty()),
            () -> assertEquals(newsRepository.findById(news.getId()).get().getTitle(), TEST_NEWS_TITLE),
            () -> assertEquals(newsRepository.findById(news.getId()).get().getShortText(), TEST_NEWS_SUMMARY),
            () -> assertEquals(newsRepository.findById(news.getId()).get().getFullText(), TEST_NEWS_TEXT)
        );
    }

    @Test
    public void givenNothing_whenSaveNewsWithCoverImageAndAdditionalImages_thenFindListWithOneElementAndFindNewsByIdAndFindImagesInNewsImageRepository() {

        News news = News.NewsBuilder.aNews()
            .withTitle(TEST_NEWS_TITLE)
            .withShortText(TEST_NEWS_SUMMARY)
            .withFullText(TEST_NEWS_TEXT)
            .withCoverImage(TEST_COVER_IMAGE)
            .build();

        NewsImage img1 = NewsImage.NewsImageBuilder.aNewsImage().withNews(news).withImageData(TEST_NEWS_IMAGE_DATA_LIST.get(0)).build();
        NewsImage img2 = NewsImage.NewsImageBuilder.aNewsImage().withNews(news).withImageData(TEST_NEWS_IMAGE_DATA_LIST.get(1)).build();
        NewsImage img3 = NewsImage.NewsImageBuilder.aNewsImage().withNews(news).withImageData(TEST_NEWS_IMAGE_DATA_LIST.get(2)).build();
        List<NewsImage> testImageList = new LinkedList<>(Arrays.asList(img1,img2,img3));

        news.setImages(testImageList);

        newsRepository.save(news);

        // test if news present
        assertAll(
            () -> assertEquals(1, newsRepository.findAll().size()),
            () -> assertEquals(3, newsImageRepository.findAll().size()),
            () -> assertNotNull(newsRepository.findById(news.getId())),

        // test properties of news if present
            () -> assertNotNull(newsRepository.findById(news.getId()).get().getCreatedAt()),
            () -> assertNotNull(newsRepository.findById(news.getId()).get().getCoverImage()),
            () -> assertEquals(newsRepository.findById(news.getId()).get().getCoverImage(), TEST_COVER_IMAGE),
            () -> assertEquals(3, newsRepository.findById(news.getId()).get().getImages().size()),
            () -> assertEquals(newsRepository.findById(news.getId()).get().getTitle(), TEST_NEWS_TITLE),
            () -> assertEquals(newsRepository.findById(news.getId()).get().getShortText(), TEST_NEWS_SUMMARY),
            () -> assertEquals(newsRepository.findById(news.getId()).get().getFullText(), TEST_NEWS_TEXT)
        );
    }

    @Test
    public void givenNothing_whenSaveNewsWithBlankTitle_thenThrowException() {

        News news = News.NewsBuilder.aNews()
            .withTitle("       ")
            .withShortText(TEST_NEWS_SUMMARY)
            .withFullText(TEST_NEWS_TEXT)
            .withCoverImage(TEST_COVER_IMAGE)
            .build();

        NewsImage img1 = NewsImage.NewsImageBuilder.aNewsImage().withNews(news).withImageData(TEST_NEWS_IMAGE_DATA_LIST.get(0)).build();
        NewsImage img2 = NewsImage.NewsImageBuilder.aNewsImage().withNews(news).withImageData(TEST_NEWS_IMAGE_DATA_LIST.get(1)).build();
        NewsImage img3 = NewsImage.NewsImageBuilder.aNewsImage().withNews(news).withImageData(TEST_NEWS_IMAGE_DATA_LIST.get(2)).build();
        List<NewsImage> testImageList = new LinkedList<>(Arrays.asList(img1,img2,img3));

        news.setImages(testImageList);

        assertThrows(ConstraintViolationException.class, () -> newsRepository.save(news));
    }

    // TODO: Test getById and delete

}
