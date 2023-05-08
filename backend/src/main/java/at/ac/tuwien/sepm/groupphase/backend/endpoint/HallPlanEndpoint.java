package at.ac.tuwien.sepm.groupphase.backend.endpoint;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.DetailedHallPlanDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.HallPlanDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.HallPlanSectionDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.HallPlanSeatDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.SeatRowDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.HallPlanMapper;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.HallPlanSectionMapper;
import at.ac.tuwien.sepm.groupphase.backend.service.HallPlanSeatService;
import at.ac.tuwien.sepm.groupphase.backend.service.HallPlanService;
import at.ac.tuwien.sepm.groupphase.backend.service.SeatRowService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.xml.bind.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.server.ResponseStatusException;

import java.lang.invoke.MethodHandles;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/v1/hallplans")
public class HallPlanEndpoint {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final HallPlanService hallPlanService;
    private final HallPlanSeatService hallPlanSeatService;

    private final HallPlanMapper hallPlanMapper;
    private final HallPlanSectionMapper hallPlanSectionMapper;
    private final SeatRowService seatRowService;

    @Autowired
    public HallPlanEndpoint(HallPlanService hallPlanService, HallPlanSeatService hallPlanSeatService, HallPlanMapper hallPlanMapper, HallPlanSectionMapper hallPlanSectionMapper, SeatRowService seatRowService) {
        this.hallPlanService = hallPlanService;
        this.hallPlanMapper = hallPlanMapper;
        this.hallPlanSectionMapper = hallPlanSectionMapper;
        this.seatRowService = seatRowService;
        this.hallPlanSeatService = hallPlanSeatService;
    }

    @Secured("ROLE_USER")
    @GetMapping
    @Operation(summary = "Get a list of all hall plans", security = @SecurityRequirement(name = "apiKey"))
    public List<HallPlanDto> findAll() {
        LOGGER.info("GET /api/v1/hallplans");
        return hallPlanMapper.hallPlanToHallPlanDto(hallPlanService.findAll());
    }

    @Secured("ROLE_ADMIN")
    @PostMapping
    @Operation(summary = "Create a new hall plan entry in the system", security = @SecurityRequirement(name = "apiKey"))
    public HallPlanDto createHallPlan(@RequestBody HallPlanDto hallplan) {
        LOGGER.info("POST /api/v1/hallplans");
        try {
            return hallPlanMapper.hallPlanToHallPlanDto(hallPlanService.createHallplan(hallplan));
        } catch (ValidationException e) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage(), e);
        }
    }

    @Secured("ROLE_USER")
    @GetMapping("/{id}")
    @Operation(summary = "Get a hall plan by id", security = @SecurityRequirement(name = "apiKey"))
    public ResponseEntity<DetailedHallPlanDto> getHallPlanById(@PathVariable Long id) {
        LOGGER.info("GET /api/v1/hallplans/{}", id);
        DetailedHallPlanDto hallPlanDto = hallPlanService.getHallPlanById(id);
        if (hallPlanDto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(hallPlanDto);
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a hall plan by id", security = @SecurityRequirement(name = "apiKey"))
    public ResponseEntity<Void> deleteHallPlanById(@PathVariable Long id) {
        LOGGER.info("DELETE /api/v1/hallplans/{}", id);
        hallPlanService.deleteHallPlanById(id);
        return ResponseEntity.noContent().build();
    }

    @Secured("ROLE_ADMIN")
    @PutMapping("/{id}")
    @Operation(summary = "Update a hall plan by id", security = @SecurityRequirement(name = "apiKey"))
    public ResponseEntity<HallPlanDto> updateHallPlanById(@PathVariable Long id, @RequestBody HallPlanDto hallPlanDto) {
        LOGGER.info("PUT /api/v1/hallplans/{}", id);
        HallPlanDto updatedHallPlanDto = hallPlanService.updateHallPlanById(id, hallPlanDto);
        if (updatedHallPlanDto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedHallPlanDto);
    }

    //*******************************************************
    //*                  SeatRow Mappings                   *
    //*******************************************************
    @Secured("ROLE_ADMIN")
    @PostMapping("{hallPlanId}/seatrows")
    @Operation(summary = "Create a new seat row in the system", security = @SecurityRequirement(name = "apiKey"))
    public SeatRowDto createSeatRow(@PathVariable Long hallPlanId, @RequestBody SeatRowDto seatRowDto) {
        LOGGER.info("POST /api/v1/{hallPlanId}/seatrows", hallPlanId);
        seatRowDto.setHallPlanId(hallPlanId);
        try {
            seatRowService.createSeatRow(seatRowDto);
        } catch (ValidationException e) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage(), e);
        }
        return seatRowDto;
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/seatrows/{id}")
    @Operation(summary = "Delete a seat row from the system", security = @SecurityRequirement(name = "apiKey"))
    public ResponseEntity<Void> deleteSeatRow(@PathVariable Long id) {
        LOGGER.info("DELETE /api/v1/seatrows/{}", id);
        boolean deleted = seatRowService.deleteSeatRowById(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @Secured("ROLE_ADMIN")
    @PutMapping("/seatrows/{id}")
    @Operation(summary = "Update an existing seat row in the system", security = @SecurityRequirement(name = "apiKey"))
    public SeatRowDto updateSeatRow(@PathVariable Long id, @RequestBody SeatRowDto seatRowDto) {
        LOGGER.info("PUT /api/v1/seatrows/{}", id);
        seatRowDto.setId(id);
        seatRowService.updateSeatRow(seatRowDto);
        return seatRowDto;
    }

    // HALLPLAN/SEATROWS        GETTERS
    @Secured("ROLE_USER")
    @GetMapping("/seatrows/{id}")
    @Operation(summary = "Get a seat row by id", security = @SecurityRequirement(name = "apiKey"))
    public ResponseEntity<SeatRowDto> getSeatRowById(@PathVariable Long id) {
        LOGGER.info("GET /api/v1/{}/seatrows/{}", id);
        SeatRowDto seatRowDto = seatRowService.getSeatRowById(id);
        return seatRowDto == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(seatRowDto);
    }

    @Secured("ROLE_USER")
    @GetMapping("{hallPlanId}/seatrows")
    @Operation(summary = "Get all seat rows of a Hallplan, in ascending rowNr order", security = @SecurityRequirement(name = "apiKey"))
    public List<SeatRowDto> getSeatRowByHallplanId(@PathVariable Long hallPlanId) {
        LOGGER.info("GET /api/v1/{}/seatrows", hallPlanId);
        List<SeatRowDto> seatRows = seatRowService.findAllSeatRowsOfHallPlan(hallPlanId);
        return seatRows;
    }

    @Secured("ROLE_USER")
    @GetMapping("/seatrows")
    @Operation(summary = "Get all seat rows in the system", security = @SecurityRequirement(name = "apiKey"))
    public List<SeatRowDto> findAllSeatRows() {
        LOGGER.info("GET /api/v1/seatrows");
        List<SeatRowDto> seatRows = seatRowService.findAllSeatRows();
        return seatRows;
    }

    //Section Deletion/Creation/Update/Getter
    @Secured("ROLE_ADMIN")
    @PostMapping("/sections")
    @Operation(summary = "Create a new section in the system", security = @SecurityRequirement(name = "apiKey"))
    public HallPlanSectionDto createSection(@RequestBody HallPlanSectionDto section) {
        LOGGER.info("POST /api/v1/sections");
        return hallPlanSectionMapper.toDto(hallPlanService.createSection(section));
    }

    @Secured("ROLE_ADMIN")
    @PutMapping("/sections/{id}")
    @Operation(summary = "Update a section in the system", security = @SecurityRequirement(name = "apiKey"))
    public HallPlanSectionDto updateSection(@PathVariable Long id, @RequestBody HallPlanSectionDto section) {
        LOGGER.info("PUT /api/v1/sections/{}", id);
        return hallPlanSectionMapper.toDto(hallPlanService.updateSection(id, section));
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/sections/{id}")
    @Operation(summary = "Delete a section from the system", security = @SecurityRequirement(name = "apiKey"))
    public void deleteSection(@PathVariable Long id) {
        LOGGER.info("DELETE /api/v1/sections/{}", id);
        hallPlanService.deleteSection(id);
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/sections/{id}")
    @Operation(summary = "Get a section from the system")
    public HallPlanSectionDto getSection(@PathVariable Long id) {
        LOGGER.info("GET /api/v1/sections/{}", id);
        return hallPlanSectionMapper.toDto(hallPlanService.getSection(id));
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/sections")
    @Operation(summary = "Get all sections from the system")
    public List<HallPlanSectionDto> getAllSections() {
        LOGGER.info("GET /api/v1/sections");
        return hallPlanService.getAllSections().stream()
            .map(hallPlanSectionMapper::toDto)
            .collect(Collectors.toList());
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("{id}/sections")
    @Operation(summary = "Get all sections from the system")
    public List<HallPlanSectionDto> getAllSectionsByHallRoomId(@PathVariable Long id) {
        LOGGER.info("GET /api/v1/sections");
        return hallPlanService.findAllByHallPlanId(id).stream()
            .map(hallPlanSectionMapper::toDto)
            .collect(Collectors.toList());
    }

    //*******************************************************
    //*                     Seat Mappings                   *
    //*******************************************************

    @Secured("ROLE_ADMIN")
    @PostMapping("/{hallPlanId}/seatrows/{seatRowId}/seats")
    @Operation(summary = "Add a new seat to a seat row")
    public ResponseEntity<HallPlanSeatDto> addSeat(@PathVariable Long hallPlanId, @PathVariable Long seatRowId, @Valid @RequestBody HallPlanSeatDto seatDto) {
        LOGGER.info("POST /api/v1/hallplans/{}/seatrows/{}/seats", hallPlanId, seatRowId);
        seatDto.setHallPlanId(hallPlanId);
        SeatRowDto seatRowDto = new SeatRowDto();
        seatRowDto.setHallPlanId(hallPlanId);
        seatRowDto.setId(seatRowId);
        seatDto.setSeatrowId(seatRowId);
        HallPlanSeatDto savedSeat = hallPlanSeatService.addSeat(seatDto);
        return ResponseEntity.created(URI.create("/api/v1/hallplans/" + hallPlanId + "/seatrows/" + seatRowId + "/seats/" + seatDto.getId())).body(savedSeat);
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("{hallPlanId}/seatrows/{seatRowId}/seats/{id}")
    @Operation(summary = "Delete a seat by id from a seat row")
    public ResponseEntity<Void> deleteSeat(@PathVariable Long hallPlanId, @PathVariable Long seatRowId, @PathVariable Long id) {
        LOGGER.info("DELETE /api/v1/hallplans/{}/seatrows/{}/seats/{}", hallPlanId, seatRowId, id);
        hallPlanSeatService.deleteSeat(id);
        return ResponseEntity.noContent().build();
    }

    @Secured("ROLE_ADMIN")
    @PutMapping("{hallPlanId}/seatrows/{seatRowId}/seats/{id}")
    @Operation(summary = "Update a seat by id in a seat row")
    public ResponseEntity<HallPlanSeatDto> updateSeat(@PathVariable Long hallPlanId, @PathVariable Long seatRowId, @PathVariable Long id, @Valid @RequestBody HallPlanSeatDto seatDto) {
        LOGGER.info("PUT /api/v1/hallplans/{}/seatrows/{}/seats/{}", hallPlanId, seatRowId, id);
        seatDto.setId(id);
        seatDto.setSeatrowId(seatRowId);
        hallPlanSeatService.updateSeat(seatDto);
        return ResponseEntity.ok(seatDto);
    }

    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping("/seatrows/seats/{id}")
    @Operation(summary = "Get a seat by id from a seat row")
    public ResponseEntity<HallPlanSeatDto> getSeatById(@PathVariable Long id) {
        LOGGER.info("GET /api/v1/hallplans/seatrows/seats/{}", id);
        HallPlanSeatDto seatDto = hallPlanSeatService.getSeatById(id);
        return ResponseEntity.ok(seatDto);
    }
}
