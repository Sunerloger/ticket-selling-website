package at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.DetailedHallPlanDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.HallPlanDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.HallPlanSeatDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.HallPlanSectionDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.SeatRowDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.HallPlan;
import at.ac.tuwien.sepm.groupphase.backend.entity.HallPlanSeat;
import at.ac.tuwien.sepm.groupphase.backend.entity.HallPlanSection;
import at.ac.tuwien.sepm.groupphase.backend.entity.SeatRow;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Mapper
public interface HallPlanMapper {
    @Named("hallPlan")
    @Mapping(target = "id", source = "id")
    HallPlanDto hallPlanToHallPlanDto(HallPlan hallPlan);

    @Mapping(target = "id", source = "id")
    List<HallPlanDto> hallPlanToHallPlanDto(List<HallPlan> hallPlan);

    @Mapping(target = "seatRows", source = "seatRows")
    DetailedHallPlanDto hallPlantoDetailedHallPlanDto(HallPlan hallPlan);

    @Mapping(target = "id", source = "id")
    HallPlan hallPlanDtoToHallPlan(HallPlanDto hallPlanDto);

    @Mapping(target = "id", source = "id")
    HallPlan detailedHallPlanDtoToHallPlan(DetailedHallPlanDto hallPlanDto);


    @Mapping(target = "seatRows", qualifiedByName = "mapSeatRows")
    DetailedHallPlanDto mapToDetailedHallPlanDto(HallPlan hallPlan);

    @Named("mapSeatRows")
    default List<SeatRowDto> mapSeatRows(List<SeatRow> seatRows) {
        if (seatRows == null) {
            return Collections.emptyList();
        }
        return seatRows.stream()
            .map(this::mapSeatRow)
            .collect(Collectors.toList());
    }

    default SeatRowDto mapSeatRow(SeatRow seatRow) {
        SeatRowDto seatRowDto = new SeatRowDto();
        seatRowDto.setId(seatRow.getId());
        seatRowDto.setRowNr(seatRow.getRowNr());
        seatRowDto.setHallPlanId(seatRowDto.getHallPlanId());
        seatRowDto.setSeats(mapSeats(seatRow.getSeats()));
        return seatRowDto;
    }

    default List<HallPlanSeatDto> mapSeats(List<HallPlanSeat> seats) {
        if (seats == null) {
            return Collections.emptyList();
        }
        return seats.stream()
            .map(this::mapSeat)
            .collect(Collectors.toList());
    }

    default HallPlanSectionDto mapHallPlanSection(HallPlanSection section) {
        HallPlanSectionDto hallPlanSectionDto = new HallPlanSectionDto();
        hallPlanSectionDto.setId(section.getId());
        hallPlanSectionDto.setName(section.getName());
        hallPlanSectionDto.setColor(section.getColor());
        hallPlanSectionDto.setPrice(section.getPrice());
        return hallPlanSectionDto;
    }

    default HallPlanSeatDto mapSeat(HallPlanSeat seat) {
        HallPlanSeatDto seatDto = new HallPlanSeatDto();
        seatDto.setId(seat.getId());
        seatDto.setStatus(seat.getStatus().toString());
        seatDto.setType(seat.getType().toString());
        seatDto.setCapacity(seat.getCapacity());
        seatDto.setSeatNr(seat.getSeatNr());
        seatDto.setSection(mapHallPlanSection(seat.getSection()));
        return seatDto;
    }

}


