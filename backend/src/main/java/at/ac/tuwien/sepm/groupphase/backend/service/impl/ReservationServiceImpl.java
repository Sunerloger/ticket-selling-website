package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EventDetailDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.HallPlanSeatDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.ReservationDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.HallPlanSeat;
import at.ac.tuwien.sepm.groupphase.backend.entity.Reservation;
import at.ac.tuwien.sepm.groupphase.backend.entity.ReservationSeat;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.repository.HallPlanSeatRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.ReservationRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.EventService;
import at.ac.tuwien.sepm.groupphase.backend.service.HallPlanSeatService;
import at.ac.tuwien.sepm.groupphase.backend.service.ReservationService;
import at.ac.tuwien.sepm.groupphase.backend.type.HallPlanSeatStatus;
import jakarta.persistence.LockModeType;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationServiceImpl implements ReservationService {
    private ReservationRepository repository;
    private HallPlanSeatService seatService;
    private EventService eventService;
    private HallPlanSeatRepository hallPlanSeatRepository; //TODO: replace with seatservice repository

    @Autowired
    public ReservationServiceImpl(ReservationRepository repository, HallPlanSeatService seatService, EventService eventService, HallPlanSeatRepository hallPlanSeatRepository) {
        this.repository = repository;
        this.seatService = seatService;
        this.eventService = eventService;
        this.hallPlanSeatRepository = hallPlanSeatRepository;
    }

    @Override
    public List<ReservationDto> getReservationsOfUser(Long userID) {
        List<Reservation> reservationList = repository.findReservationsByUserIdOrderByDate(userID);
        List<ReservationDto> reservationDtoList = new ArrayList<>();

        for (Reservation reservation : reservationList) {
            if (reservation.getReservationSeatsList().isEmpty()) {
                return null;
            }

            EventDetailDto event = eventService.getEventById(1L); //TODO: get correct Event
            ReservationDto reservationDto = new ReservationDto();
            reservationDto.setEvent(event);
            reservationDto.setReservationDate(reservation.getDate());
            reservationDto.setReservationNr(reservation.getReservationNr());

            List<HallPlanSeatDto> seatDtoList = new ArrayList<>();
            for (int j = 0; j < reservation.getReservationSeatsList().size(); j++) {
                seatDtoList.add(seatService.getSeatById(reservation.getReservationSeatsList().get(j).getSeat()));
            }
            reservationDto.setReservedSeats(seatDtoList);
            reservationDtoList.add(reservationDto);
        }

        return reservationDtoList;
    }

    @Override
    @Transactional
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    public void deleteReservation(Long reservationNr, Long userID) {

        //maybe implement this whole function in HallplanSeatService
        Reservation reservation = repository.findReservationByReservationNr(reservationNr);

        if (reservation == null){
            return;
        }

        if (!reservation.getUserId().equals(userID)){
            return;
        }

        for (ReservationSeat reservationSeat:reservation.getReservationSeatsList()) {
            Optional<HallPlanSeat> optSeat = hallPlanSeatRepository.findById(reservationSeat.getSeat());
            if (optSeat.isPresent()){
                HallPlanSeat seat = optSeat.get();
                if (seat.getStatus().equals(HallPlanSeatStatus.RESERVED)){
                    seat.setStatus(HallPlanSeatStatus.FREE);
                    hallPlanSeatRepository.saveAndFlush(seat);
                }
            }
        }

        repository.deleteReservationByReservationNr(reservationNr);
    }

    @Override
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    public void addReservation(List<HallPlanSeatDto> itemDtoList, Long userID) {
        List<HallPlanSeat> seatList = new ArrayList<>();
        for (HallPlanSeatDto item: itemDtoList) {
            Optional<HallPlanSeat> optSeat = hallPlanSeatRepository.findById(item.getId());
            if (optSeat.isEmpty()){
                throw new NotFoundException();
            }
            HallPlanSeat seat = optSeat.get();
            seatList.add(seat);
        }

        //TODO: check if items are part of the same event

        List<HallPlanSeat> freeSeatList = new ArrayList<>();
        for (HallPlanSeat seat: seatList){
            if (seat.getStatus().equals(HallPlanSeatStatus.FREE)){
                seat.setStatus(HallPlanSeatStatus.RESERVED);
                hallPlanSeatRepository.saveAndFlush(seat);
                freeSeatList.add(seat);
            }
        }

        Reservation reservation = new Reservation();
        List<ReservationSeat> reservationSeatList = new ArrayList<>();
        for (HallPlanSeat seat: freeSeatList){
            ReservationSeat reservationSeat = new ReservationSeat();
            reservationSeat.setSeat(seat.getId());
            reservationSeatList.add(reservationSeat);
        }

        reservation.setUserId(userID);
        reservation.setReservationSeatsList(reservationSeatList);
        reservation.setDate(LocalDate.now());
        repository.save(reservation);
    }


}
