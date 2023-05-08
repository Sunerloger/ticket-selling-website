package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.DetailedHallPlanDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.HallPlanDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.HallPlanSectionDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.HallPlanMapper;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.HallPlanSectionMapper;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.SeatRowMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.HallPlan;
import at.ac.tuwien.sepm.groupphase.backend.entity.HallPlanSection;
import at.ac.tuwien.sepm.groupphase.backend.entity.SeatRow;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.repository.HallPlanRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.HallPlanSectionRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.SeatRowRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.HallPlanService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.xml.bind.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Optional;

@Service
public class HallPlanServiceImpl implements HallPlanService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final HallPlanRepository hallPlanRepository;

    private final HallPlanMapper hallPlanMapper;

    private final HallPlanSectionMapper hallPlanSectionMapper;

    private final HallPlanSectionRepository hallPlanSectionRepository;
    private final SeatRowRepository seatRowRepository;
    private final SeatRowMapper seatRowMapper;

    public HallPlanServiceImpl(HallPlanRepository hallPlanRepository, HallPlanMapper hallPlanMapper, HallPlanSectionMapper hallPlanSectionMapper, HallPlanSectionRepository hallPlanSectionRepository, SeatRowRepository seatRowRepository, SeatRowMapper seatRowMapper) {
        this.hallPlanRepository = hallPlanRepository;
        this.hallPlanMapper = hallPlanMapper;
        this.hallPlanSectionMapper = hallPlanSectionMapper;
        this.hallPlanSectionRepository = hallPlanSectionRepository;
        this.seatRowRepository = seatRowRepository;
        this.seatRowMapper = seatRowMapper;
    }

    @Override
    public List<HallPlan> findAll() {
        LOGGER.debug("Find all hall plans");
        List<HallPlan> hallPlans = hallPlanRepository.findAllHallPlans();
        return hallPlans;
    }

    @Override
    public HallPlan createHallplan(HallPlanDto hallplan) throws ValidationException {
        LOGGER.debug("Create new hall plan");
        Optional<HallPlan> existingHallPlan = hallPlanRepository.findHallPlanById(hallplan.getId());
        if (existingHallPlan.isPresent()) {
            throw new ValidationException("Hallplan with id:" + hallplan.getId() + " already exists");
        }
        return hallPlanRepository.save(hallPlanMapper.hallPlanDtoToHallPlan(hallplan));
    }
    @Transactional
    @Override
    public DetailedHallPlanDto getHallPlanById(Long id) {
        Optional<HallPlan> hallPlanEntityWithoutSeats = hallPlanRepository.findHallPlanById(id);
        List<SeatRow> seatRows = seatRowRepository.findAllByHallplanIdWithSeats(id);
        if(hallPlanEntityWithoutSeats.isPresent()) hallPlanEntityWithoutSeats.get().setSeatRows(seatRows);
        return hallPlanEntityWithoutSeats.map(hallPlanMapper::mapToDetailedHallPlanDto).orElse(null);
    }

    @Override
    public void deleteHallPlanById(Long id) {
        hallPlanRepository.deleteById(id);
    }

    @Override
    public HallPlanDto updateHallPlanById(Long id, HallPlanDto hallPlanDto) {
        Optional<HallPlan> hallPlanOptional = hallPlanRepository.findById(id);
        if (hallPlanOptional.isPresent()) {
            HallPlan hallPlan = hallPlanOptional.get();
            hallPlan.setName(hallPlanDto.getName());
            hallPlan.setDescription(hallPlanDto.getDescription());
            HallPlan updatedhallPlan = hallPlanRepository.save(hallPlan);
            return hallPlanMapper.hallPlanToHallPlanDto(updatedhallPlan);
        }
        return null;
    }

    @Override
    public HallPlanSection createSection(HallPlanSectionDto sectionDto) {
        HallPlanSection section = new HallPlanSection();
        section.setName(sectionDto.getName());
        section.setColor(sectionDto.getColor());
        section.setPrice(sectionDto.getPrice());
        return hallPlanSectionRepository.save(section);
    }

    @Override
    public HallPlanSection updateSection(Long id, HallPlanSectionDto sectionDto) {
        HallPlanSection section = getSectionById(id);
        section.setName(sectionDto.getName());
        section.setColor(sectionDto.getColor());
        section.setPrice(sectionDto.getPrice());
        return hallPlanSectionRepository.save(section);
    }

    @Override
    public void deleteSection(Long id) {
        HallPlanSection section = getSectionById(id);
        hallPlanSectionRepository.delete(section);
    }

    @Override
    public HallPlanSection getSection(Long id) {
        return getSectionById(id);
    }

    @Override
    public List<HallPlanSection> getAllSections() {
        return hallPlanSectionRepository.findAll();
    }

    @Override
    public List<HallPlanSection> findAllByHallPlanId(Long id) {
        //List<HallPlanSection> testList = hallPlanSectionRepository.findByHallPlanId(id);
        return null;
    }

    private HallPlanSection getSectionById(Long id) {
        return hallPlanSectionRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Hall Plan Section with id " + id + " not found"));
    }
}
