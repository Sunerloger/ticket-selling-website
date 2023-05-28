package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EventDetailDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.EventMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.Event;
import at.ac.tuwien.sepm.groupphase.backend.repository.EventRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.EventService;
import jakarta.persistence.criteria.Predicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.beans.Expression;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

@Service
public class EventServiceImpl implements EventService {
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;

    public EventServiceImpl(EventRepository eventRepository, EventMapper eventMapper) {
        this.eventRepository = eventRepository;
        this.eventMapper = eventMapper;
    }

    @Override
    public Event create(EventDetailDto event) {
        LOG.trace("create({})", event);
        Event debug = eventMapper.eventDetailDtoToEvent(event);
        return eventRepository.save(debug);
    }

    @Override
    public Page<Event> findAllPagesByDate(int pageIndex) {
        Pageable pageable = PageRequest.of(pageIndex, 20, Sort.by("title").ascending());

        LOG.debug("Find all event entries by pageable: {}", pageable);
        return eventRepository.findAll(pageable);
    }

    @Override
    public Page<Event> findAllPagesByDateAndAuthorAndLocation(int pageIndex, LocalDate fromDate, LocalDate toDate, String artist, String location) {
        Pageable pageable = PageRequest.of(pageIndex, 20, Sort.by("title").ascending());
        String eventDatesLocation = "eventDatesLocation";
        Specification<Event> specification = (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.isTrue(root.get("id"));
            if (fromDate != null) {
                predicate = criteriaBuilder.greaterThanOrEqualTo(root.join(eventDatesLocation).get("date"), fromDate);
            }

            if (toDate != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.lessThanOrEqualTo(root.join(eventDatesLocation).get("date"), toDate));
            }

            if (artist != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("artist"), "%" + artist + "%"));
            }

            if (location != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.join(eventDatesLocation).get("address"), "%" + location + "%"));
            }

            return predicate;
        };
        return eventRepository.findAll(specification, pageable);
    }

    @Override
    public EventDetailDto getEventById(Long id) {
        LOG.trace("getEventById({})", id);
        List<Event> events = new ArrayList<>();
        events.add(eventRepository.getEventById(id));
        return eventMapper.eventToEventDetailDto(events).get(0);
    }

}
