package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.SeatRowDto;
import jakarta.xml.bind.ValidationException;

import java.util.List;

public interface SeatRowService {
    List<SeatRowDto> findAllSeatRows();

    List<SeatRowDto> findAllSeatRowsOfHallPlan(Long id);

    SeatRowDto createSeatRow(SeatRowDto seatRowDto) throws ValidationException;

    SeatRowDto getSeatRowById(Long id);

    SeatRowDto updateSeatRow(SeatRowDto seatRowDto);

    boolean deleteSeatRowById(Long id);
}
