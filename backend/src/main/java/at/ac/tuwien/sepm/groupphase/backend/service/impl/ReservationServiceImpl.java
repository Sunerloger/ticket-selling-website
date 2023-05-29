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
import jakarta.persistence.LockModeType;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {
    private ReservationRepository repository;
    private HallPlanSeatService seatService;
    private EventService eventService;
    private SeatRowServiceImpl rowService;

    @Autowired
    public ReservationServiceImpl(ReservationRepository repository, HallPlanSeatService seatService, EventService eventService, SeatRowServiceImpl rowService) {
        this.repository = repository;
        this.seatService = seatService;
        this.eventService = eventService;
        this.rowService = rowService;
    }

    @Override
    public List<ReservationDto> getReservationsOfUser(Long userId) {
        List<Reservation> reservationList = repository.findReservationsByUserIdOrderByDate(userId);
        List<ReservationDto> reservationDtoList = new ArrayList<>();

        for (Reservation reservation : reservationList) {
            if (reservation.getReservationSeatsList().isEmpty()) {
                break; //TODO: actually this shouldnt happen (every reservation should have seats)
            }

            List<SeatDto> seatDtoList = new ArrayList<>();
            List<ReservationSeat> reservationSeatList = reservation.getReservationSeatsList();

            for (ReservationSeat reservationSeat : reservationSeatList) {
                HallPlanSeatDto hallPlanSeatDto = seatService.getSeatById(reservationSeat.getSeatId());
                SeatRowDto rowDto = rowService.getSeatRowById(hallPlanSeatDto.getSeatrowId());
                seatDtoList.add(new SeatDto(hallPlanSeatDto, rowDto));
            }

            ReservationDto reservationDto = new ReservationDto();
            reservationDto.setReservedSeats(seatDtoList);

            EventDetailDto event = eventService.getEventById(1L); //TODO: get correct Event
            reservationDto.setEvent(event);

            reservationDto.setReservationDate(reservation.getDate());
            reservationDto.setReservationNr(reservation.getReservationNr());

            reservationDtoList.add(reservationDto);
        }

        return reservationDtoList;
    }

    @Override
    public ReservationDto getReservationOfUser(Long reservationNr, Long userId) {
        Reservation reservation = repository.findReservationByReservationNr(reservationNr);
        if (!reservation.getUserId().equals(userId)) {
            return null;
        }

        if (reservation.getReservationSeatsList().isEmpty()) {
            return null;
        }

        List<SeatDto> seatDtoList = new ArrayList<>();
        List<ReservationSeat> reservationSeatList = reservation.getReservationSeatsList();

        for (ReservationSeat reservationSeat : reservationSeatList) {
            HallPlanSeatDto hallPlanSeatDto = seatService.getSeatById(reservationSeat.getSeatId());
            SeatRowDto rowDto = rowService.getSeatRowById(hallPlanSeatDto.getSeatrowId());
            seatDtoList.add(new SeatDto(hallPlanSeatDto, rowDto));
        }

        ReservationDto reservationDto = new ReservationDto();
        reservationDto.setReservedSeats(seatDtoList);

        EventDetailDto event = eventService.getEventById(1L); //TODO: get correct Event
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
            return; //Todo: No Content
        }

        if (!reservation.getUserId().equals(userId)) {
            return; //TODO: No Content (to not have side channels)
        }

        for (ReservationSeat reservationSeat : reservation.getReservationSeatsList()) {
            seatService.cancelReservation(reservationSeat.getSeatId());
        }
        repository.deleteReservationByReservationNr(reservationNr);
    }

    @Override
    @Lock(LockModeType.PESSIMISTIC_WRITE)
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
