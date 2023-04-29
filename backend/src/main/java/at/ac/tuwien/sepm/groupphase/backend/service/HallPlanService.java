package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.entity.HallPlan;

import java.util.List;

public interface HallPlanService {

    /**
     * Find all hall plans stored in the system
     *
     * @return Returns a list of hall plan entities
     */
    List<HallPlan> findAll();
}
