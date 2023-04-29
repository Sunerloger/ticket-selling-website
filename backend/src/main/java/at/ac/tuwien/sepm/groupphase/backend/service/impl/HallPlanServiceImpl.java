package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.HallPlanDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.HallPlanMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.HallPlan;
import at.ac.tuwien.sepm.groupphase.backend.repository.HallPlanRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.HallPlanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Optional;

@Service
public class HallPlanServiceImpl implements HallPlanService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final HallPlanRepository hallPlanRepository;

    private final HallPlanMapper hallPlanMapper;

    public HallPlanServiceImpl(HallPlanRepository hallPlanRepository, HallPlanMapper hallPlanMapper) {
        this.hallPlanRepository = hallPlanRepository;
        this.hallPlanMapper = hallPlanMapper;
    }

    @Override
    public List<HallPlan> findAll() {
        LOGGER.debug("Find all hall plans");
        List<HallPlan> hallPlans = hallPlanRepository.findAllHallPlans();
        return hallPlans;
    }

    @Override
    public HallPlan createHallplan(HallPlanDto hallplan) {
        LOGGER.debug("Create new hall plan");
        return hallPlanRepository.save(hallPlanMapper.hallPlanDtoToHallPlan(hallplan));
    }

    @Override
    public HallPlanDto getHallPlanById(Long id) {
        Optional<HallPlan> hallPlanEntity = hallPlanRepository.findById(id);
        return hallPlanEntity.map(hallPlanMapper::hallPlanToHallPlanDto).orElse(null);
    }

    @Override
    public void deleteHallPlanById(Long id) {
        hallPlanRepository.deleteById(id);
    }

    @Override
    public HallPlanDto updateHallPlanById(Long id, HallPlanDto hallPlanDto) {
        Optional<HallPlan> HallPlanOptional = hallPlanRepository.findById(id);
        if (HallPlanOptional.isPresent()) {
            HallPlan HallPlan = HallPlanOptional.get();
            HallPlan.setName(hallPlanDto.getName());
            HallPlan.setDescription(hallPlanDto.getDescription());
            HallPlan updatedHallPlan = hallPlanRepository.save(HallPlan);
            return hallPlanMapper.hallPlanToHallPlanDto(updatedHallPlan);
        }
        return null;
    }
}
