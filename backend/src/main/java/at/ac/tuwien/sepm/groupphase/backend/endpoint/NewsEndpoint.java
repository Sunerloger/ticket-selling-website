package at.ac.tuwien.sepm.groupphase.backend.endpoint;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.news.AbbreviatedNewsDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.news.DetailedNewsDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.news.NewsInquiryDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.NewsMapper;
import at.ac.tuwien.sepm.groupphase.backend.service.NewsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.lang.invoke.MethodHandles;

@RestController
@RequestMapping(value = NewsEndpoint.BASE_PATH)
public class NewsEndpoint {

    static final String BASE_PATH = "/api/v1/news";
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final NewsService newsService;
    private final NewsMapper newsMapper;

    @Autowired
    public NewsEndpoint(NewsService newsService, NewsMapper newsMapper) {
        this.newsService = newsService;
        this.newsMapper = newsMapper;
    }

    @Secured("ROLE_ADMIN")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    @Operation(summary = "Publish a new news-entry", security = @SecurityRequirement(name = "apiKey"))
    public DetailedNewsDto create(@Valid @RequestBody NewsInquiryDto newsDto) {
        LOGGER.info("POST {} body: {}", BASE_PATH, newsDto);

        return newsMapper.newsToDetailedNewsDto(
            newsService.publishNews(newsMapper.newsInquiryDtoWithImagesToNews(newsDto)));
    }

    @Secured("ROLE_USER")
    @GetMapping
    @Operation(summary = "Get list of news without details", security = @SecurityRequirement(name = "apiKey"))
    public Page<AbbreviatedNewsDto> findAll(@RequestParam(defaultValue = "0") int pageIndex) {
        LOGGER.info("GET {}/news", BASE_PATH);

        return newsService.findAllPagedByCreatedAt(pageIndex).map(newsMapper::newsToAbbreviatedNewsDto);
    }
}
