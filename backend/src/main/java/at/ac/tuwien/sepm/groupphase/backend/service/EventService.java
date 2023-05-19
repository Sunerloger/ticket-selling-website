package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EventDetailDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Event;
import org.springframework.data.domain.Page;

public interface EventService {
    /**
     * Creates a event with the given attributes.
     *
     * @param event the event to create
     * @return the created event
     */
    Event create(EventDetailDto event);

    /**
     * Finds all pages of events sorted by Date.
     *
     * @param pageIndex index of page to load
     * @return page of 20 events sorted by date
     */
    Page<Event> findAllPagesByDate(int pageIndex);
}
