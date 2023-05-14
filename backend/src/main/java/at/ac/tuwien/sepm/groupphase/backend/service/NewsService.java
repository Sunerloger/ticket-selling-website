package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.entity.News;

public interface NewsService {

    /**
     * Publish a news entry.
     *
     * @param news to publish
     * @return published news entry
     */
    News publishNews(News news);

}
