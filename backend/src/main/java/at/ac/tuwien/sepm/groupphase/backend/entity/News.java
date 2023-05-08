package at.ac.tuwien.sepm.groupphase.backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Lob;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.validation.constraints.NotEmpty;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Entity
public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Column(nullable = false)
    private String title;

    @Column(nullable = false, name = "abbreviated_text", length = 500)
    private String shortText;

    @Column(nullable = false, name = "full_text", length = 10000)
    private String fullText;

    @Column(nullable = false, name = "created_on_timestamp")
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt;

    // TODO: store images in filesystem?
    @Lob
    @Column(name = "cover_image", columnDefinition = "BLOB")
    private String coverImage;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "news")
    private List<NewsImage> images = new LinkedList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public List<NewsImage> getImages() {
        return images;
    }

    public void setImages(List<NewsImage> images) {
        this.images = images;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof News news)) {
            return false;
        }
        return Objects.equals(id, news.id)
            && Objects.equals(title, news.title)
            && Objects.equals(shortText, news.shortText)
            && Objects.equals(fullText, news.fullText)
            && Objects.equals(createdAt, news.createdAt)
            && Objects.equals(coverImage, news.coverImage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, shortText, fullText, createdAt, coverImage);
    }

    @Override
    public String toString() {
        return "News{"
            + "id=" + id
            + ", title='" + title + '\''
            + ", shortText='" + shortText + '\''
            + ", fullText='" + fullText + '\''
            + ", createdAt=" + createdAt
            + '}';
    }


    public static final class NewsBuilder {
        private Long id;
        private String title;
        private String shortText;
        private String fullText;
        private LocalDateTime createdAt;
        private String coverImage;
        private List<NewsImage> images;

        private NewsBuilder() {
        }

        public static NewsBuilder aNews() {
            return new NewsBuilder();
        }

        public NewsBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public NewsBuilder withTitle(String title) {
            this.title = title;
            return this;
        }

        public NewsBuilder withShortText(String shortText) {
            this.shortText = shortText;
            return this;
        }

        public NewsBuilder withFullText(String fullText) {
            this.fullText = fullText;
            return this;
        }

        public NewsBuilder withCreatedAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public NewsBuilder withCoverImage(String coverImage) {
            this.coverImage = coverImage;
            return this;
        }

        public NewsBuilder withImages(List<NewsImage> images) {
            this.images = images;
            return this;
        }

        public News build() {
            News news = new News();
            news.setId(id);
            news.setTitle(title);
            news.setFullText(fullText);
            news.setShortText(shortText);
            news.setCreatedAt(createdAt);
            news.setCoverImage(coverImage);
            news.setImages(images);
            return news;
        }
    }
}
