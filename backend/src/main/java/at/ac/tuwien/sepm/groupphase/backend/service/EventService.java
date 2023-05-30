package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EventDetailDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Event;
import jakarta.xml.bind.ValidationException;
import org.springframework.data.domain.Page;

import java.time.LocalDate;

public interface EventService {
    /**
     * Creates a event with the given attributes.
     *
     * @param event the event to create
     * @return the created event
     */
    Event create(EventDetailDto event) throws ValidationException;

    EventDetailDto getEventById(Long id);

    /**
     * Finds all pages of events sorted by Date.
     *
     * @param pageIndex index of page to load
     * @return page of max 20 events sorted by date
     */
    Page<Event> findAllPagesByDate(int pageIndex);

    /**
     * Finds all pages of events with the filters given.
     *
     * @param pageIndex index of page to load
     * @param fromDate  the earliest date that is searched
     * @param toDate    the latest data that is searched
     * @param author    the author of given event
     * @param location  the location that is searched for
     * @return page of max 20 events sorted by date
     */
    Page<Event> findAllPagesByDateAndAuthorAndLocation(int pageIndex, LocalDate fromDate, LocalDate toDate, String author, String location);
}
