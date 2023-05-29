package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.hallplan.DetailedHallPlanDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.hallplan.HallPlanDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.hallplan.HallPlanSectionDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.HallPlanMapper;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.HallPlanSectionMapper;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.SeatRowMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.HallPlan;
import at.ac.tuwien.sepm.groupphase.backend.entity.HallPlanSeat;
import at.ac.tuwien.sepm.groupphase.backend.entity.HallPlanSection;
import at.ac.tuwien.sepm.groupphase.backend.entity.News;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
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

    public HallPlanServiceImpl(HallPlanRepository hallPlanRepository, HallPlanMapper hallPlanMapper, HallPlanSectionMapper hallPlanSectionMapper, HallPlanSectionRepository hallPlanSectionRepository, SeatRowRepository seatRowRepository,
                               SeatRowMapper seatRowMapper) {
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
    public HallPlan createHallPlan(HallPlanDto hallplan) throws ValidationException {
        LOGGER.debug("Create new hall plan");
        Optional<HallPlan> existingHallPlan = hallPlanRepository.findHallPlanById(hallplan.getId());
        if (existingHallPlan.isPresent()) {
            throw new ValidationException("Hallplan with id:" + hallplan.getId() + " already exists");
        }
        return hallPlanRepository.save(hallPlanMapper.hallPlanDtoToHallPlan(hallplan));
    }


    @Override
    public DetailedHallPlanDto getHallPlanById(Long id) {
        LOGGER.debug("Get hall plan by id: {}", id);
        Optional<HallPlan> hallPlanEntityWithoutSeats = hallPlanRepository.findHallPlanById(id);
        List<SeatRow> seatRows = seatRowRepository.findAllByHallplanIdWithSeats(id);
        if (hallPlanEntityWithoutSeats.isPresent()) {
            hallPlanEntityWithoutSeats.get().setSeatRows(seatRows);
        } else {
            throw new NotFoundException("Hall Plan with id " + id + " was not found in the system");
        }
        DetailedHallPlanDto detailed = hallPlanEntityWithoutSeats.map(hallPlanMapper::mapToDetailedHallPlanDto).orElse(null);
        return detailed;
        //return hallPlanEntityWithoutSeats.map(hallPlanMapper::mapToDetailedHallPlanDto).orElse(null);
    }

    @Override
    public void deleteHallPlanById(Long id) {
        LOGGER.debug("Delete hall plan by id: {}", id);
        if (!hallPlanRepository.existsById(id)) {
            throw new NotFoundException("Hall Plan not found with id: " + id);
        }
        hallPlanRepository.deleteById(id);
    }

    @Override
    public HallPlanDto updateHallPlanById(Long id, HallPlanDto hallPlanDto) {
        LOGGER.debug("Update hall plan by id: {}", id);
        Optional<HallPlan> hallPlanOptional = hallPlanRepository.findById(id);
        if (hallPlanOptional.isPresent()) {
            HallPlan hallPlan = hallPlanOptional.get();
            hallPlan.setName(hallPlanDto.getName());
            hallPlan.setDescription(hallPlanDto.getDescription());
            HallPlan updatedhallPlan = hallPlanRepository.save(hallPlan);
            return hallPlanMapper.hallPlanToHallPlanDto(updatedhallPlan);
        } else {
            throw new EntityNotFoundException("Hall Plan with id " + id + " was not found in the system");
        }
    }

    @Override
    public HallPlanSection createSection(HallPlanSectionDto sectionDto) {
        LOGGER.debug("Create new hall plan section");
        HallPlanSection section = new HallPlanSection();
        section.setName(sectionDto.getName());
        section.setColor(sectionDto.getColor());
        section.setPrice(sectionDto.getPrice());
        section.setHallPlanId(sectionDto.getHallPlanId());
        return hallPlanSectionRepository.save(section);
    }

    @Override
    public HallPlanSection updateSection(Long id, HallPlanSectionDto sectionDto) {
        LOGGER.debug("Update hall plan section by id: {}", id);
        HallPlanSection section = getSectionById(id);
        section.setName(sectionDto.getName());
        section.setColor(sectionDto.getColor());
        section.setPrice(sectionDto.getPrice());
        section.setHallPlanId(sectionDto.getHallPlanId());
        return hallPlanSectionRepository.save(section);
    }

    @Override
    public void deleteSection(Long id) {
        LOGGER.debug("Delete hall plan section by id: {}", id);
        HallPlanSection section = getSectionById(id);
        hallPlanSectionRepository.delete(section);
    }

    @Override
    public HallPlanSection getSection(Long id) {
        LOGGER.debug("Get hall plan section by id: {}", id);
        return getSectionById(id);
    }

    @Override
    public List<HallPlanSection> getAllSections() {
        LOGGER.debug("Get all hall plan sections");
        return hallPlanSectionRepository.findAll();
    }

    @Override
    public List<HallPlanSection> findAllByHallPlanId(Long id) {
        LOGGER.debug("Find all hall plan sections by hall plan id: {}", id);
        //List<HallPlanSection> testList = hallPlanSectionRepository.findByHallPlanId(id);
        return null;
    }

    @Override
    public List<HallPlanSectionDto> findAllSectionsByHallPlanIdWithCounts(Long hallplanId) {
        List<Object[]> counts = hallPlanRepository.findAllSectionsByHallPlanIdCounts(hallplanId);
        List<Object[]> zeroCounts = hallPlanRepository.findHallPlanCountsById(hallplanId);
        List<HallPlanSection> sections = new ArrayList<HallPlanSection>();
        List<Long> existIdList = new ArrayList<>();
        for (Object[] count : counts) {
            HallPlanSection section = (HallPlanSection) count[0];
            section.setCount((Long) count[1]);
            sections.add((HallPlanSection) count[0]);
            existIdList.add(section.getId());
        }
        for (Object[] zeroCount : zeroCounts) {
            HallPlanSection section = (HallPlanSection) zeroCount[0];
            section.setCount(0L);
            boolean exist = false;
            for (Long id : existIdList) {
                if (id.longValue() == section.getId()) {
                    exist = true;
                    break;
                }
            }
            if (!exist) {
                sections.add(section);
            }

        }
        List<HallPlanSectionDto> list = hallPlanSectionMapper.toDto(sections);
        return list;
    }

    @Override
    public Page<HallPlan> findPageOfHallplans(int pageIndex) {
        Pageable pageable = PageRequest.of(pageIndex, 5, Sort.by("name").ascending());
        return hallPlanRepository.findAll(pageable);
    }

    @Override
    public List<HallPlanSectionDto> findAllSectionsByHallPlanId(Long hallplanId) {
        return hallPlanSectionMapper.toDto(hallPlanRepository.findAllSectionsByHallPlanId(hallplanId));
    }


    private HallPlanSection getSectionById(Long id) {
        LOGGER.debug("Find section by id: {}", id);
        return hallPlanSectionRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Hall Plan Section with id " + id + " not found"));
    }
}
