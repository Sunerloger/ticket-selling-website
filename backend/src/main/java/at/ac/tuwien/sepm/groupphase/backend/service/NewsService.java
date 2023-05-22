package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.entity.News;
import org.springframework.data.domain.Page;

public interface NewsService {

    /**
     * Publish a news entry.
     *
     * @param news to publish
     * @return published news entry
     */
    News publishNews(News news);

    /**
     * Find 20 news entries ordered by published at date (descending) on the page specified by {@code pageIndex}.
     *
     * @param pageIndex index of page to load
     *
     * @return page of 20 news entries ordered descending by the date of creation specified by {@code pageIndex}
     */
    Page<News> findAllPagedByCreatedAt(int pageIndex);
}
