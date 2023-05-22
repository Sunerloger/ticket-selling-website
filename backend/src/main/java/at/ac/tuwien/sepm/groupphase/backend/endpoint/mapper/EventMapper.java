package at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EventDetailDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Event;
import at.ac.tuwien.sepm.groupphase.backend.entity.EventDate;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Mapper
public interface EventMapper {
    @Named("event")
    @Mapping(target = "id", source = "id")
    EventDetailDto eventToEventDetailDto(Event event);

    @IterableMapping(qualifiedByName = "event")
    @Mapping(target = "id", source = "id")
    List<EventDetailDto> eventToEventDetailDto(List<Event> events);

    @IterableMapping(qualifiedByName = "event")
    @Mapping(target = "id", source = "id")
    Event eventDetailDtoToEvent(EventDetailDto event);

    @Named("mapLocalDateToEventDate")
    default List<LocalDate> mapLocalDateToEventDate(List<EventDate> eventDates) {
        if (eventDates == null) {
            return Collections.emptyList();
        }
        return eventDates.stream()
            .map(this::eventDatesMapper)
            .collect(Collectors.toList());
    }

    @Named("mapEventDateToLocalDate")
    default List<EventDate> mapEventDateToLocalDate(List<LocalDate> localDates) {
        if (localDates == null) {
            return Collections.emptyList();
        }
        return localDates.stream()
            .map(this::localDatesMapper)
            .collect(Collectors.toList());
    }

    default LocalDate eventDatesMapper(EventDate eventDate) {
        return eventDate.getDate();
    }

    default EventDate localDatesMapper(LocalDate localDate) {
        EventDate eventDate = new EventDate();
        eventDate.setDate(localDate);
        return eventDate;
    }





}
