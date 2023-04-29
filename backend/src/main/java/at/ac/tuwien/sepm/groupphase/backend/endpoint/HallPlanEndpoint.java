package at.ac.tuwien.sepm.groupphase.backend.endpoint;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.HallPlanDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.HallPlanSectionDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.HallPlanMapper;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.HallPlanSectionMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.HallPlan;
import at.ac.tuwien.sepm.groupphase.backend.service.HallPlanService;
import at.ac.tuwien.sepm.groupphase.backend.service.impl.HallPlanServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
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
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/v1/hallplans")
public class HallPlanEndpoint {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final HallPlanService hallPlanService;
    private final HallPlanMapper hallPlanMapper;
    private final HallPlanSectionMapper hallPlanSectionMapper;

    @Autowired
    public HallPlanEndpoint(HallPlanService hallPlanService, HallPlanMapper hallPlanMapper, HallPlanSectionMapper hallPlanSectionMapper) {
        this.hallPlanService = hallPlanService;
        this.hallPlanMapper = hallPlanMapper;
        this.hallPlanSectionMapper = hallPlanSectionMapper;
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
        return hallPlanMapper.hallPlanToHallPlanDto(hallPlanService.createHallplan(hallplan));
    }

    @Secured("ROLE_USER")
    @GetMapping("/{id}")
    @Operation(summary = "Get a hall plan by id", security = @SecurityRequirement(name = "apiKey"))
    public ResponseEntity<HallPlanDto> getHallPlanById(@PathVariable Long id) {
        LOGGER.info("GET /api/v1/hallplans/{}", id);
        HallPlanDto hallPlanDto = hallPlanService.getHallPlanById(id);
        if(hallPlanDto == null) return ResponseEntity.notFound().build();
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

    @GetMapping("/sections/{id}")
    @Operation(summary = "Get a section from the system")
    public HallPlanSectionDto getSection(@PathVariable Long id) {
        LOGGER.info("GET /api/v1/sections/{}", id);
        return hallPlanSectionMapper.toDto(hallPlanService.getSection(id));
    }

    @GetMapping("/sections")
    @Operation(summary = "Get all sections from the system")
    public List<HallPlanSectionDto> getAllSections() {
        LOGGER.info("GET /api/v1/sections");
        return hallPlanService.getAllSections().stream()
            .map(hallPlanSectionMapper::toDto)
            .collect(Collectors.toList());
    }
    @GetMapping("{id}/sections")
    @Operation(summary = "Get all sections from the system")
    public List<HallPlanSectionDto> getAllSectionsByHallRoomId(@PathVariable Long id) {
        LOGGER.info("GET /api/v1/sections");
        return hallPlanService.findAllByHallPlanId(id).stream()
            .map(hallPlanSectionMapper::toDto)
            .collect(Collectors.toList());
    }




}
