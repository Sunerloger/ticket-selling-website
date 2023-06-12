package at.ac.tuwien.sepm.groupphase.backend.basetest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public interface TestData {

    Long ID = 1L;
    String TEST_NEWS_TITLE = "Title";
    String TEST_NEWS_SUMMARY = "Summary";
    String TEST_NEWS_TEXT = "TestMessageText";
    String TEST_COVER_IMAGE = "data:image/png;base64,IAMACOVERIMAGE==";
    List<String> TEST_NEWS_IMAGE_DATA_LIST = new LinkedList<>(Arrays.asList("data:image/png;base64,IMAGEONE==", "data:image/jpeg;base64,IMAGETWO==", "data:image/png;base64,IMAGETHREE=="));
    LocalDateTime TEST_NEWS_PUBLISHED_AT =
        LocalDateTime.of(2019, 11, 13, 12, 15, 0, 0);

    String BASE_URI = "/api/v1";
    String MESSAGE_BASE_URI = BASE_URI + "/messages";
    String NEWS_BASE_URI = BASE_URI + "/news";
    String CART_BASE_URI = BASE_URI + "/cart";
    String ADMIN_USER = "adminTest@email.com";
    List<String> ADMIN_ROLES = new ArrayList<>() {
        {
            add("ROLE_ADMIN");
            add("ROLE_USER");
        }
    };
    String DEFAULT_USER = "userTest@email.com";
    List<String> USER_ROLES = new ArrayList<>() {
        {
            add("ROLE_USER");
        }
    };

}
