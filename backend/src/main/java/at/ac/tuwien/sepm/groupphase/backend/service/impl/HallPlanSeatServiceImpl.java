package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.HallPlanSeatDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.HallPlanSeatMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.HallPlan;
import at.ac.tuwien.sepm.groupphase.backend.entity.HallPlanSeat;
import at.ac.tuwien.sepm.groupphase.backend.entity.SeatRow;
import at.ac.tuwien.sepm.groupphase.backend.repository.HallPlanSeatRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.SeatRowRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.HallPlanSeatService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HallPlanSeatServiceImpl implements HallPlanSeatService {

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
        Optional<SeatRow> seatRow = seatRowRepository.findById(seatDto.getSeatrow().getId());
        if (seatRow.isEmpty()) {
            throw new EntityNotFoundException("Seat row not found with id: " + seatDto.getSeatrow().getId());
        }
        seatRow = seatRowRepository.findByIdAndHallPlanId(seatDto.getSeatrow().getId(), seatDto.getHallPlanId());
        if (seatRow.isEmpty()) {
            throw new EntityNotFoundException("Seat row does not exist in hallplan with id: " + seatDto.getHallPlanId());
        }
        HallPlanSeat seat = seatMapper.toEntity(seatDto);
        seat = seatRepository.save(seat);
        return seatMapper.toDto(seat);
    }


    @Override
    public HallPlanSeatDto getSeatById(Long seatId) {
        Optional<HallPlanSeat> seat = seatRepository.getSeatById(seatId);
        return seat.map(seatMapper::toDto).orElse(null);
    }

    @Override
    public List<HallPlanSeatDto> getAllSeatsBySeatRow(Long hallPlanId, Long seatRowId) {
        return null;
    }

    @Override
    public HallPlanSeatDto updateSeat(HallPlanSeatDto seatDto) {
        HallPlanSeat seat = seatMapper.toEntity(seatDto);
        seatRepository.save(seat);
        return seatMapper.toDto(seat);
    }

    @Override
    public void deleteSeat(Long seatId) {
        seatRepository.deleteById(seatId);
    }
    /*
    @Override
    public HallPlanSeatDto getSeat(Long hallPlanId, Long seatRowId, Long seatId) {
        HallPlanSeat seat = seatRepository.findByIdAndSeatRow_HallPlan_IdAndSeatRow_Id(seatId, hallPlanId, seatRowId)
            .orElseThrow(() -> new EntityNotFoundException("Seat not found with id: " + seatId));
        return seatMapper.toDto(seat);
    }

    @Override
    public List<HallPlanSeatDto> getAllSeatsBySeatRow(Long hallPlanId, Long seatRowId) {
        List<HallPlanSeat> seats = seatRepository.findAllBySeatRow_HallPlan_IdAndSeatRow_Id(hallPlanId, seatRowId);
        return seats.stream().map(seatMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public HallPlanSeatDto updateSeat(Long hallPlanId, Long seatRowId, Long seatId, SeatDto seatDto) {
        HallPlanSeat seat = seatRepository.findByIdAndSeatRow_HallPlan_IdAndSeatRow_Id(seatId, hallPlanId, seatRowId)
            .orElseThrow(() -> new EntityNotFoundException("Seat not found with id: " + seatId));
        seatMapper.updateFromDto(seatDto, seat);
        seat = seatRepository.save(seat);
        return seatMapper.toDto(seat);
    }

    @Override
    public void deleteSeat(Long hallPlanId, Long seatRowId, Long seatId) {
        HallPlanSeat seat = seatRepository.findByIdAndSeatRow_HallPlan_IdAndSeatRow_Id(seatId, hallPlanId, seatRowId)
            .orElseThrow(() -> new EntityNotFoundException("Seat not found with id: " + seatId));
        seatRepository.delete(seat);
    }

    */
}

