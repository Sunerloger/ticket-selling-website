package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.news.NewsInquiryDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.*;
import org.springframework.data.domain.Page;
import org.springframework.http.*;

public interface NewsService {

    /**
     * Publish a news entry.
     *
     * @param news to publish
     * @return published news entry
     */
    News publishNews(NewsInquiryDto news);

    /**
     * Find 20 news entries ordered by published at date (descending) on the page specified by {@code pageIndex}.
     *
     * @param pageIndex index of page to load
     * @param loadAlreadyRead determines if news entries that have already been read by the user (true) or ones the user
     *                        has neven seen (true) should get loaded
     * @param userId specifies the user who requested to load the news page
     * @return page of 20 news entries ordered descending by the date of creation specified by {@code pageIndex}
     */
    Page<News> findAllPagedByCreatedAt(int pageIndex, boolean loadAlreadyRead, Long userId);

    /**
     * Fetch news entry with the specified id from the database and set relation news_read between user and news.
     *
     * @param id of news to fetch
     * @return entity of news entry
     */
    News getById(Long id);

    /**
     * Delete news entry with the specified id from the database.
     *
     * @param id of news to delete
     */
    void deleteById(Long id);

    // TODO javadoc
    ResponseEntity<Void> putRelation(Long id, ApplicationUser user);
}
