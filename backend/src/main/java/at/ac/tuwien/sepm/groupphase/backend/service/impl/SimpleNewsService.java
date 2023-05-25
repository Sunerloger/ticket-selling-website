package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.entity.News;
import at.ac.tuwien.sepm.groupphase.backend.entity.NewsImage;
import at.ac.tuwien.sepm.groupphase.backend.repository.NewsRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.NewsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;

@Service
public class SimpleNewsService implements NewsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final NewsRepository newsRepository;

    public SimpleNewsService(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    @Override
    public News publishNews(News news) {
        LOGGER.debug("Publish new news {}", news);

        for (NewsImage img : news.getImages()) {
            img.setNews(news);
        }

        return newsRepository.save(news);
    }

    @Override
    public Page<News> findAllPagedByCreatedAt(int pageIndex) {
        Pageable pageable = PageRequest.of(pageIndex, 20, Sort.by("createdAt").descending());

        LOGGER.debug("Find all news entries by pageable: {}", pageable);

        return newsRepository.findAll(pageable);
    }

}
