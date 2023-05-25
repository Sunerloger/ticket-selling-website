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
import org.springframework.http.*;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.lang.invoke.MethodHandles;
import java.util.List;

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
            newsService.publishNews(newsDto));
    }

    @Secured("ROLE_USER")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    @Operation(summary = "Get list of news without details", security = @SecurityRequirement(name = "apiKey"))
    public List<AbbreviatedNewsDto> findAll(@RequestParam(defaultValue = "0") int pageIndex) {
        LOGGER.info("GET {}", BASE_PATH);

        return newsService.findAllPagedByCreatedAt(pageIndex).map(newsMapper::newsToAbbreviatedNewsDto).toList();
    }

    @Secured("ROLE_USER")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("{id}")
    @Operation(summary = "Get detailed news by id without short description", security = @SecurityRequirement(name = "apiKey"))
    public DetailedNewsDto findOneById(@PathVariable Long id) {
        LOGGER.info("GET {}/{}", BASE_PATH, id);

        return newsMapper.newsToDetailedNewsDto(newsService.getById(id));
    }

    @Secured("ROLE_ADMIN")
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("{id}")
    @Operation(summary = "Delete news entry with the given id", security = @SecurityRequirement(name = "apiKey"))
    public void deleteOneById(@PathVariable Long id) {
        LOGGER.info("DELETE {}/{}", BASE_PATH, id);
        newsService.deleteById(id);
    }
}
