package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.entity.HallPlan;
import at.ac.tuwien.sepm.groupphase.backend.repository.HallPlanRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.HallPlanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.util.List;

@Service
public class HallPlanServiceImpl implements HallPlanService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final HallPlanRepository hallPlanRepository;

    public HallPlanServiceImpl(HallPlanRepository hallPlanRepository) {
        this.hallPlanRepository = hallPlanRepository;
    }

    @Override
    public List<HallPlan> findAll() {
        LOGGER.debug("Find all hall plans");
        List<HallPlan> hallPlans = hallPlanRepository.findAllHallPlans();
        return hallPlans;
    }
}
