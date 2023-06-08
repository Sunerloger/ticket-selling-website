package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EventDetailDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.PerformanceDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Event;
import jakarta.persistence.LockModeType;
import jakarta.xml.bind.ValidationException;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Lock;

import java.time.LocalDate;

public interface EventService {
    /**
     * Creates a event with the given attributes.
     *
     * @param event the event to create
     * @return the created event
     */
    Event create(EventDetailDto event) throws ValidationException;

    /**
     * Fetches the Event with the corresponding id.
     *
     * @param id the id of the event
     * @return the found EventDetailDto or NULL if the event doesn't exist
     */
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


    /**
     * Fetches the Event that has an Eventdate with the given hallplanId.
     *
     * @param hallplanId the id of the hallplan
     * @return returns the EventDetailDto corresponding to the hallplanId
     */
    EventDetailDto getEventFromHallplanId(Long hallplanId);

    PerformanceDto getPerformanceFromHallplanId(Long hallplanId);

    void incrementSoldTickets(Long hallplanId);

    @Lock(LockModeType.OPTIMISTIC)
    void decrementSoldTickets(Long hallplanId);
}
