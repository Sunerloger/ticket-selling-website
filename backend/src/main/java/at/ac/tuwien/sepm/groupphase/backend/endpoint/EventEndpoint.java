package at.ac.tuwien.sepm.groupphase.backend.endpoint;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.AbbreviatedEventDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EventDetailDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.PerformanceDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.EventMapper;
import at.ac.tuwien.sepm.groupphase.backend.service.EventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.xml.bind.ValidationException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.lang.invoke.MethodHandles;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/events")
public class EventEndpoint {
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final EventService eventService;
    private final EventMapper eventMapper;

    @Autowired
    public EventEndpoint(EventService eventService, EventMapper eventMapper) {
        this.eventService = eventService;
        this.eventMapper = eventMapper;
    }

    @Secured("ROLE_ADMIN")
    @PostMapping
    @Operation(summary = "Create a new event entry in the system", security = @SecurityRequirement(name = "apiKey"))
    public EventDetailDto createEvent(@RequestBody EventDetailDto event) {
        LOG.info("POST /api/v1/events");
        try {
            return eventMapper.eventToEventDetailDto(eventService.create(event));
        } catch (ValidationException e) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage(), e);
        }
    }

    @Secured("ROLE_USER")
    @GetMapping("/performance/{id}")
    @Operation(summary = "Get Information of Performance", security = @SecurityRequirement(name = "apiKey"))
    public PerformanceDto getPerformance(@PathVariable Long id) {
        LOG.info("GET /api/v1/events/performance/{}", id);
        return eventService.getPerformanceFromHallplanId(id);
    }

    @Secured("ROLE_USER")
    @GetMapping
    @Operation(summary = "Get list of events without details", security = @SecurityRequirement(name = "apiKey"))
    public List<AbbreviatedEventDto> findAllDefault(
        @RequestParam(defaultValue = "0") int pageIndex,
        @RequestParam(required = false) String artist,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate untilDate,
        @RequestParam(required = false) String location
    ) {
        LOG.info("GET {}/events", "/api/v1/events");

        if (artist != null || fromDate != null || untilDate != null || location != null) {
            LOG.info("called with param");
            return eventService.findAllPagesByDateAndAuthorAndLocation(pageIndex, fromDate, untilDate, artist, location)
                .map(eventMapper::eventToAbbreviatedEventDto)
                .toList();
        } else {
            LOG.info("called without param");
            return eventService.findAllPagesByDate(pageIndex)
                .map(eventMapper::eventToAbbreviatedEventDto)
                .toList();
        }
    }
}
