package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EventDetailDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Event;

public interface EventService {
    /**
     * Creates a event with the given attributes
     * @param event, the event to create
     * @return the created event
     */
    Event create(EventDetailDto event);
}
