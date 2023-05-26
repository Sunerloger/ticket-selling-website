package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.HallPlanSeatDto;
import jakarta.transaction.Transactional;

import java.util.List;

public interface HallPlanSeatService {

    HallPlanSeatDto addSeat(HallPlanSeatDto seatDto);

    HallPlanSeatDto getSeatById(Long seatId);

    List<HallPlanSeatDto> getAllSeatsBySeatRow(Long hallPlanId, Long seatRowId);

    HallPlanSeatDto updateSeat(HallPlanSeatDto seatDto);

    void deleteSeat(Long seatId);

    boolean doesSeatExist(Long seatId);

    boolean purchaseReservedSeat(Long seatId);

    boolean tryReserveSeat(Long seatId);

    @Transactional
    boolean cancelReservation(Long seatId);
}
