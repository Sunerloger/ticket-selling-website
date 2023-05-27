package at.ac.tuwien.sepm.groupphase.backend.entity;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "event")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "event_id")
    private List<EventDate> eventDatesLocation;

    private LocalTime duration;

    private String category;

    private String description;

    private String artist;

    @Column(columnDefinition = "TEXT")
    private String image;

    public Event() {
    }

    public Event(
        Long id,
        String title,
        List<EventDate> eventDatesLocation,
        LocalTime duration,
        String category,
        String description,
        String image,
        String artist) {
        this.id = id;
        this.title = title;
        this.eventDatesLocation = eventDatesLocation;
        this.duration = duration;
        this.category = category;
        this.description = description;
        this.image = image;
        this.artist = artist;
    }

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

    public List<EventDate> getEventDatesLocation() {
        return eventDatesLocation;
    }

    public void setEventDatesLocation(List<EventDate> eventsDatesLocation) {
        this.eventDatesLocation = eventsDatesLocation;
    }

    public LocalTime getDuration() {
        return duration;
    }

    public void setDuration(LocalTime duration) {
        this.duration = duration;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }
}
