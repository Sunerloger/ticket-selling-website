package at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.SeatRowDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.SeatRow;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper
public interface SeatRowMapper {

    @Named("seatRow")
    @Mapping(target = "hallPlan", source = "hallPlan")
    SeatRowDto toDto(SeatRow seatRow);

    @IterableMapping(qualifiedByName = "seatRow")
    @Mapping(target = "hallPlan", source = "hallPlan")
    List<SeatRowDto> toDto(List<SeatRow> seatRows);

    @IterableMapping(qualifiedByName = "seatRow")
    @Mapping(target = "hallPlan", source = "hallPlan")
    List<SeatRow> toEntity(List<SeatRowDto> seatRows);

    @Named("seatRow")
    @Mapping(target = "hallPlan", source = "hallPlan")
    SeatRow toEntity(SeatRowDto seatRowDto);

}
