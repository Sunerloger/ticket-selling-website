package at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.HallPlanDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.HallPlan;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.util.List;

@Mapper
public interface HallPlanMapper {
    @Named("hallPlan")
    HallPlanDto hallPlanToHallPlanDto(HallPlan hallPlan);

    @IterableMapping(qualifiedByName = "hallPlan")
    List<HallPlanDto> hallPlanToHallPlanDto(List<HallPlan> hallPlan);

    @IterableMapping(qualifiedByName = "hallPlan")
    HallPlan hallPlanDtoToHallPlan(HallPlanDto hallPlanDto);
}
