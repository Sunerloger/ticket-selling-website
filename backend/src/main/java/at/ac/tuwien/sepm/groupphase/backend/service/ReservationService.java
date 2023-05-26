package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.CartItemDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.HallPlanSeatDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.ReservationDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.SeatDto;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import jakarta.transaction.Transactional;

import java.util.List;

public interface ReservationService {


    List<ReservationDto> getReservationsOfUser(Long userID);

    @Transactional
    void deleteReservation(Long reservationNr, Long userID);

    void addReservation(List<SeatDto> itemDtoList, Long userID);
}
