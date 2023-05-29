package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.news.NewsInquiryDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.NewsMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.News;
import at.ac.tuwien.sepm.groupphase.backend.entity.Event;
import at.ac.tuwien.sepm.groupphase.backend.entity.NewsImage;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.repository.NewsRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.EventRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.NewsService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.util.Optional;

@Service
public class SimpleNewsService implements NewsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final NewsRepository newsRepository;
    private final EventRepository eventRepository;
    private final NewsMapper newsMapper;

    public SimpleNewsService(NewsRepository newsRepository, EventRepository eventRepository, NewsMapper newsMapper) {
        this.newsRepository = newsRepository;
        this.eventRepository = eventRepository;
        this.newsMapper = newsMapper;
    }

    @Override
    @Transactional
    public News publishNews(NewsInquiryDto newsDto) {

        Long eventId = newsDto.getEventId();

        News news = newsMapper.newsInquiryDtoWithImagesToNewsWithoutEvent(newsDto);

        if (eventId != null) {

            if (!eventRepository.existsById(eventId)) {
                throw new NotFoundException("Event not found with id: " + eventId);
            }

            Optional<Event> eventOpt = eventRepository.findById(eventId);

            if (eventOpt.isPresent()) {
                news.setEvent(eventOpt.get());
            } else {
                news.setEvent(null);
            }
        } else {
            news.setEvent(null);
        }

        LOGGER.debug("Publish new news {}", news);

        for (NewsImage img : news.getImages()) {
            img.setNews(news);
        }

        return newsRepository.save(news);
    }

    @Override
    @Transactional
    public Page<News> findAllPagedByCreatedAt(int pageIndex) {
        Pageable pageable = PageRequest.of(pageIndex, 20, Sort.by("createdAt").descending());

        LOGGER.debug("Find all news entries by pageable: {}", pageable);

        return newsRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public News getById(Long id) {
        LOGGER.debug("Find news by id {}", id);
        Optional<News> newsOptional = newsRepository.findById(id);
        if (newsOptional.isPresent()) {
            News news = newsOptional.get();
            news.getImages().size(); // lazy loading
            return news;
        } else {
            throw new NotFoundException(String.format("Could not find news with id %s", id));
        }
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        LOGGER.debug("Delete news by id {}", id);

        if (!newsRepository.existsById(id)) {
            throw new NotFoundException("News not found with id: " + id);
        }

        newsRepository.deleteById(id);
    }
}
