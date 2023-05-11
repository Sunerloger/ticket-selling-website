package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.news;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class NewsInquiryDto {


    @NotNull(message = "Title must not be null")
    @NotEmpty(message = "Title must not be empty")
    @Size(max = 20)
    private String title;

    @NotEmpty(message = "Short Text must not be empty")
    @Size(max = 100)
    private String shortText;

    @NotEmpty(message = "Full Text must not be empty")
    @Size(max = 10000)
    private String fullText;

    // TODO: test for real picture format

    private String coverImage;

    @NotNull
    private List<String> images = new LinkedList<>();


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShortText() {
        return shortText;
    }

    public void setShortText(String shortText) {
        this.shortText = shortText;
    }

    public String getFullText() {
        return fullText;
    }

    public void setFullText(String fullText) {
        this.fullText = fullText;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NewsInquiryDto that)) {
            return false;
        }
        return Objects.equals(title, that.title)
            && Objects.equals(shortText, that.shortText)
            && Objects.equals(fullText, that.fullText)
            && Objects.equals(coverImage, that.coverImage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, shortText, fullText, coverImage);
    }

    @Override
    public String toString() {
        return "NewsInquiryDto{"
            + "title='" + title + '\''
            + ", shortText='" + shortText + '\''
            + ", fullText='" + fullText + '\''
            + '}';
    }


    public static final class NewsInquiryDtoBuilder {
        private String title;
        private String shortText;
        private String fullText;
        private String coverImage;
        private List<String> images;

        private NewsInquiryDtoBuilder() {
        }

        public static NewsInquiryDto.NewsInquiryDtoBuilder aNewsInquiryDto() {
            return new NewsInquiryDto.NewsInquiryDtoBuilder();
        }

        public NewsInquiryDto.NewsInquiryDtoBuilder withTitle(String title) {
            this.title = title;
            return this;
        }

        public NewsInquiryDto.NewsInquiryDtoBuilder withShortText(String shortText) {
            this.shortText = shortText;
            return this;
        }

        public NewsInquiryDto.NewsInquiryDtoBuilder withFullText(String fullText) {
            this.fullText = fullText;
            return this;
        }

        public NewsInquiryDto.NewsInquiryDtoBuilder withCoverImage(String coverImage) {
            this.coverImage = coverImage;
            return this;
        }

        public NewsInquiryDto.NewsInquiryDtoBuilder withImages(List<String> images) {
            this.images = images;
            return this;
        }

        public NewsInquiryDto build() {
            NewsInquiryDto newsInquiryDto = new NewsInquiryDto();
            newsInquiryDto.setTitle(title);
            newsInquiryDto.setShortText(shortText);
            newsInquiryDto.setFullText(fullText);
            newsInquiryDto.setCoverImage(coverImage);
            newsInquiryDto.setImages(images);
            return newsInquiryDto;
        }
    }
}
