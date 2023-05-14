package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.HallPlanSeatDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.HallPlanSeatMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.HallPlanSeat;
import at.ac.tuwien.sepm.groupphase.backend.entity.SeatRow;
import at.ac.tuwien.sepm.groupphase.backend.repository.HallPlanSeatRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.SeatRowRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.HallPlanSeatService;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Optional;

@Service
public class HallPlanSeatServiceImpl implements HallPlanSeatService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final HallPlanSeatRepository seatRepository;
    private final HallPlanSeatMapper seatMapper;
    private final SeatRowRepository seatRowRepository;

    @Autowired
    public HallPlanSeatServiceImpl(HallPlanSeatRepository seatRepository, HallPlanSeatMapper seatMapper, SeatRowRepository seatRowRepository) {
        this.seatRepository = seatRepository;
        this.seatMapper = seatMapper;
        this.seatRowRepository = seatRowRepository;
    }

    @Override
    public HallPlanSeatDto addSeat(HallPlanSeatDto seatDto) {
        LOGGER.debug("Add new seat");
        Optional<SeatRow> seatRow = seatRowRepository.findById(seatDto.getSeatrowId());
        if (seatRow.isEmpty()) {
            throw new EntityNotFoundException("Seat row not found with id: " + seatDto.getSeatrowId());
        }
        //seatRow = seatRowRepository.findByIdAndHallPlanId(seatDto.getSeatrowId(), seatDto.getHallPlanId());
        if(seatDto.getId() != null) {
            seatDto.setId(null);
        }
        HallPlanSeat seat = seatMapper.toEntity(seatDto);
        seat = seatRepository.save(seat);
        return seatMapper.toDto(seat);
    }

    @Override
    public HallPlanSeatDto getSeatById(Long seatId) {
        LOGGER.debug("Get seat by ID");
        Optional<HallPlanSeat> seat = seatRepository.getSeatById(seatId);
        return seat.map(seatMapper::toDto).orElse(null);
    }

    @Override
    public List<HallPlanSeatDto> getAllSeatsBySeatRow(Long hallPlanId, Long seatRowId) {
        LOGGER.debug("Get all seats by seat row");
        return null;
    }

    @Override
    public HallPlanSeatDto updateSeat(HallPlanSeatDto seatDto) {
        LOGGER.debug("Update seat by ID");
        HallPlanSeat seat = seatMapper.toEntity(seatDto);
        seatRepository.save(seat);
        return seatMapper.toDto(seat);
    }

    @Override
    public void deleteSeat(Long seatId) {
        LOGGER.debug("Delete seat by ID");
        seatRepository.deleteById(seatId);
    }

}

