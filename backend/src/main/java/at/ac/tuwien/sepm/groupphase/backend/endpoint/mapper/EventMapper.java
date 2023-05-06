package at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EventDetailDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Event;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper
public interface EventMapper {
    @Named("event")
    @Mapping(target = "id", source = "id")
    EventDetailDto eventToEventDetailDto(Event event);

    @IterableMapping(qualifiedByName = "event")
    @Mapping(target = "id", source = "id")
    List<EventDetailDto> eventToEventDetailDto(List<Event> events);

    @IterableMapping(qualifiedByName = "hallPlan")
    @Mapping(target = "id", source = "id")
    Event eventDetailDtoToEvent(EventDetailDto event);

}
