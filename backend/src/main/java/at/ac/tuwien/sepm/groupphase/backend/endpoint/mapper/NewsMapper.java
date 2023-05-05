package at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.news.AbbreviatedNewsDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.news.DetailedNewsDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.news.ImageDataDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.news.NewsInquiryDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.News;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper
public interface NewsMapper {

    ImageDataDto imageDataToImageDataDto(byte[] imageData);

    @Named("imageDataDtoToImageData")
    static byte[] toByteArray(ImageDataDto imageDataDto) {
        return imageDataDto == null ? null : imageDataDto.getImageData();
    }

    AbbreviatedNewsDto newsToAbbreviatedNewsDto(News news);

    DetailedNewsDto newsToDetailedNewsDto(News news);

    @Mapping(source = "coverImage", target = "coverImage", qualifiedByName = "imageDataDtoToImageData")
    News newsInquiryDtoWithImagesToNews(NewsInquiryDto newsInquiryDto);

    NewsInquiryDto newsToNewsInquiryDto(News news);
}
