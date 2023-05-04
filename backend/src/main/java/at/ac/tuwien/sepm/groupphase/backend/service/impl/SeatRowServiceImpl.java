package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.SeatRowDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.SeatRowMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.HallPlan;
import at.ac.tuwien.sepm.groupphase.backend.entity.SeatRow;
import at.ac.tuwien.sepm.groupphase.backend.repository.HallPlanRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.SeatRowRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.SeatRowService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.xml.bind.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SeatRowServiceImpl implements SeatRowService {

    private final SeatRowRepository seatRowRepository;
    private final SeatRowMapper seatRowMapper;

    private final HallPlanRepository hallPlanRepository;

    @Autowired
    public SeatRowServiceImpl(SeatRowRepository seatRowRepository, SeatRowMapper seatRowMapper, HallPlanRepository hallPlanRepository) {
        this.seatRowRepository = seatRowRepository;
        this.seatRowMapper = seatRowMapper;
        this.hallPlanRepository = hallPlanRepository;
    }

    @Override
    public List<SeatRowDto> findAllSeatRows() {
        List<SeatRow> seatRowEntities = seatRowRepository.findAllSeatRowsWithHallPlans();
        return seatRowMapper.toDto(seatRowEntities);
    }

    @Override
    public SeatRowDto createSeatRow(SeatRowDto seatRowDto) throws ValidationException {
        Long hallPlanId = seatRowDto.getHallPlan().getId();
        HallPlan hallPlan = hallPlanRepository.findById(hallPlanId)
            .orElseThrow(() -> new EntityNotFoundException("HallPlan with id " + hallPlanId + " not found"));

        Optional<SeatRow> existingSeatRow = seatRowRepository.findByRowNrAndHallPlan(seatRowDto.getRowNr(), hallPlan);
        if (existingSeatRow.isPresent()) {
            throw new ValidationException("SeatRow with rowNr " + seatRowDto.getRowNr() + " and hallPlanId " + hallPlanId + " already exists");
        }

        SeatRow seatRow = seatRowMapper.toEntity(seatRowDto);
        seatRow.setHallPlan(hallPlan);

        SeatRow savedSeatRow = seatRowRepository.save(seatRow);

        return seatRowMapper.toDto(savedSeatRow);
    }

    @Override
    public SeatRowDto getSeatRowById(Long id) {
        Optional<SeatRow> seatRowEntityOptional = seatRowRepository.findById(id);
        return seatRowEntityOptional.map(seatRowMapper::toDto).orElse(null);
    }

    @Override
    public SeatRowDto updateSeatRow(SeatRowDto seatRowDto) {
        Optional<SeatRow> seatRowEntityOptional = seatRowRepository.findById(seatRowDto.getId());
        if (seatRowEntityOptional.isPresent()) {
            SeatRow seatRowEntity = seatRowMapper.toEntity(seatRowDto);
            SeatRow updatedSeatRow = seatRowRepository.save(seatRowEntity);
            return seatRowMapper.toDto(updatedSeatRow);
        }
        return null;
    }

    @Override
    public boolean deleteSeatRowById(Long id) {
        Optional<SeatRow> seatRowEntityOptional = seatRowRepository.findById(id);
        if (seatRowEntityOptional.isPresent()) {
            seatRowRepository.deleteById(id);
            return true;
        }
        return false;
    }
}