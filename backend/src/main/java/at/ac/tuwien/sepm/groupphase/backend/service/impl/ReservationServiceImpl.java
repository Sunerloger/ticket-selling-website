package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.hallplan.HallPlanSeatDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.SeatDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.ReservationDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.SeatRowDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EventDetailDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Reservation;
import at.ac.tuwien.sepm.groupphase.backend.entity.ReservationSeat;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.repository.ReservationRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.EventService;
import at.ac.tuwien.sepm.groupphase.backend.service.HallPlanSeatService;
import at.ac.tuwien.sepm.groupphase.backend.service.ReservationService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {
    private final ReservationRepository repository;
    private final HallPlanSeatService seatService;
    private final EventService eventService;
    private final SeatRowServiceImpl rowService;

    @Autowired
    public ReservationServiceImpl(ReservationRepository repository, HallPlanSeatService seatService, EventService eventService, SeatRowServiceImpl rowService) {
        this.repository = repository;
        this.seatService = seatService;
        this.eventService = eventService;
        this.rowService = rowService;
    }

    @Override
    public List<ReservationDto> getReservationsOfUser(Long userId) {
        List<Reservation> reservationList = repository.findReservationsByUserIdOrderByReservationNrDesc(userId);
        List<ReservationDto> reservationDtoList = new ArrayList<>();

        for (Reservation reservation : reservationList) {
            if (reservation.getReservationSeatsList().isEmpty()) {
                break; //TODO: actually this shouldnt happen (every reservation should have seats)
            }

            reservationDtoList.add(getReservationOfUser(reservation.getReservationNr(), userId));
        }

        return reservationDtoList;
    }

    @Override
    public ReservationDto getReservationOfUser(Long reservationNr, Long userId) throws NotFoundException {
        Reservation reservation = repository.findReservationByReservationNr(reservationNr);
        if (!reservation.getUserId().equals(userId)) {
            throw new NotFoundException();
        }

        if (reservation.getReservationSeatsList().isEmpty()) {
            throw new NotFoundException();
        }

        List<SeatDto> seatDtoList = new ArrayList<>();
        List<ReservationSeat> reservationSeatList = reservation.getReservationSeatsList();
        SeatRowDto rowDto = null;
        for (ReservationSeat reservationSeat : reservationSeatList) {
            HallPlanSeatDto hallPlanSeatDto = seatService.getSeatById(reservationSeat.getSeatId());
            rowDto = rowService.getSeatRowById(hallPlanSeatDto.getSeatrowId());
            seatDtoList.add(new SeatDto(hallPlanSeatDto, rowDto));
        }

        ReservationDto reservationDto = new ReservationDto();
        reservationDto.setReservedSeats(seatDtoList);

        if (rowDto == null) {
            throw new RuntimeException();
        }

        EventDetailDto event = eventService.getEventFromHallplanId(rowDto.getHallPlanId());
        reservationDto.setEvent(event);

        reservationDto.setReservationDate(reservation.getDate());
        reservationDto.setReservationNr(reservation.getReservationNr());

        return reservationDto;
    }


    @Override
    @Transactional
    public void deleteReservation(Long reservationNr, Long userId) {
        Reservation reservation = repository.findReservationByReservationNr(reservationNr);

        if (reservation == null) {
            throw new NotFoundException();
        }

        if (!reservation.getUserId().equals(userId)) {
            throw new NotFoundException();
        }

        for (ReservationSeat reservationSeat : reservation.getReservationSeatsList()) {
            seatService.cancelReservation(reservationSeat.getSeatId());
        }
        repository.deleteReservationByReservationNr(reservationNr);
    }

    @Override
    public void addReservation(List<SeatDto> itemDtoList, Long userId) {
        if (itemDtoList.isEmpty()) {
            return;
        }
        for (SeatDto item : itemDtoList) {
            if (!seatService.doesSeatExist(item.getId())) {
                throw new NotFoundException();
            }
        }

        //TODO: check if items are part of the same event

        Reservation reservation = new Reservation();
        reservation.setDate(LocalDate.now());
        reservation.setUserId(userId);

        List<ReservationSeat> reservationSeatList = new ArrayList<>();

        for (SeatDto item : itemDtoList) {
            if (seatService.tryReserveSeat(item.getId())) {
                reservationSeatList.add(new ReservationSeat(item.getId()));
                //TODO: Inform user that not all seats were Reserved
            }
        }

        reservation.setReservationSeatsList(reservationSeatList);

        if (!reservation.getReservationSeatsList().isEmpty()) {
            repository.save(reservation);
        }
        //TODO: some kind of error (reservation has no items)
    }


}
