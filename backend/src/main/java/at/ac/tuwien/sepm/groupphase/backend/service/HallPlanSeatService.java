package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.HallPlanSeatDto;

import java.util.List;

public interface HallPlanSeatService {

    HallPlanSeatDto addSeat(HallPlanSeatDto seatDto);

    HallPlanSeatDto getSeatById(Long seatId);

    List<HallPlanSeatDto> getAllSeatsBySeatRow(Long hallPlanId, Long seatRowId);

    HallPlanSeatDto updateSeat(HallPlanSeatDto seatDto);

    void deleteSeat(Long seatId);
}
