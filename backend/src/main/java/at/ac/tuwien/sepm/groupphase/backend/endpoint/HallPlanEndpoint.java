package at.ac.tuwien.sepm.groupphase.backend.endpoint;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.HallPlanDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.HallPlanMapper;
import at.ac.tuwien.sepm.groupphase.backend.service.HallPlanService;
import at.ac.tuwien.sepm.groupphase.backend.service.impl.HallPlanServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/hallplans")
public class HallPlanEndpoint {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final HallPlanService hallPlanService;

    private final HallPlanMapper hallPlanMapper;

    @Autowired
    public HallPlanEndpoint(HallPlanService hallPlanService,HallPlanMapper hallPlanMapper) {
        this.hallPlanService = hallPlanService;
        this.hallPlanMapper = hallPlanMapper;
    }

    @Secured("ROLE_USER")
    @GetMapping
    @Operation(summary = "Get a list of all hall plans", security = @SecurityRequirement(name = "apiKey"))
    public List<HallPlanDto> findAll() {
        LOGGER.info("GET /api/v1/hallplans");
        return hallPlanMapper.hallPlanToHallPlanDto(hallPlanService.findAll());
    }
}
