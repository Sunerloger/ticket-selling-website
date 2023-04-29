package at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper;


import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.HallPlanSectionDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.HallPlanSection;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

@Mapper
public interface HallPlanSectionMapper {

    @Named("hallPlanSection")
    HallPlanSectionDto toDto(HallPlanSection section);

    @IterableMapping(qualifiedByName = "hallPlanSection")
    HallPlanSection toEntity(HallPlanSectionDto sectionDto);
}
