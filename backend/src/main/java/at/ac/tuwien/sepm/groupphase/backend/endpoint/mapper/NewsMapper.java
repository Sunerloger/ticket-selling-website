package at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.news.AbbreviatedNewsDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.news.DetailedNewsDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.news.NewsInquiryDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.News;
import at.ac.tuwien.sepm.groupphase.backend.entity.NewsImage;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.LinkedList;
import java.util.List;

@Mapper
public interface NewsMapper {

    @Named("stringListToNewsImageList")
    static List<NewsImage> toNewsImageList(List<String> images) {
        if (images == null) {
            return null;
        }
        List<NewsImage> entityImages = new LinkedList<>();
        for (String str : images) {
            entityImages.add(NewsImage.NewsImageBuilder.aNewsImage().withImageData(str).build());
        }
        return entityImages;
    }

    @Named("newsImageListToStringList")
    static List<String> toStringList(List<NewsImage> images) {
        if (images == null) {
            return null;
        }
        List<String> stringImages = new LinkedList<>();
        for (NewsImage img : images) {
            stringImages.add(img.getImageData());
        }
        return stringImages;
    }

    @Named("abbreviatedNews")
    AbbreviatedNewsDto newsToAbbreviatedNewsDto(News news);

    @IterableMapping(qualifiedByName = "abbreviatedNews")
    List<AbbreviatedNewsDto> newsToAbbreviatedNewsDto(List<News> news);

    @Mapping(source = "images", target = "images", qualifiedByName = "newsImageListToStringList")
    DetailedNewsDto newsToDetailedNewsDto(News news);

    @Mapping(source = "images", target = "images", qualifiedByName = "stringListToNewsImageList")
    News newsInquiryDtoWithImagesToNews(NewsInquiryDto newsInquiryDto);

    @Mapping(source = "images", target = "images", qualifiedByName = "newsImageListToStringList")
    NewsInquiryDto newsToNewsInquiryDto(News news);
}
