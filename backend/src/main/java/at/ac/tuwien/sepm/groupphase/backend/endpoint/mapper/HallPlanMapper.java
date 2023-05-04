package at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.HallPlanDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.HallPlan;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper
public interface HallPlanMapper {
    @Named("hallPlan")
    @Mapping(target = "id", source = "id")
    HallPlanDto hallPlanToHallPlanDto(HallPlan hallPlan);

    @IterableMapping(qualifiedByName = "hallPlan")
    @Mapping(target = "id", source = "id")
    List<HallPlanDto> hallPlanToHallPlanDto(List<HallPlan> hallPlan);

    @IterableMapping(qualifiedByName = "hallPlan")
    @Mapping(target = "id", source = "id")
    HallPlan hallPlanDtoToHallPlan(HallPlanDto hallPlanDto);
}
