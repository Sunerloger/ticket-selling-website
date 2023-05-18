package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.hallplan.DetailedHallPlanDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.hallplan.HallPlanDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.hallplan.HallPlanSeatBulkDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.hallplan.HallPlanSeatDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.hallplan.HallPlanSectionDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.HallPlan;
import at.ac.tuwien.sepm.groupphase.backend.entity.HallPlanSection;
import jakarta.xml.bind.ValidationException;

import java.util.List;

public interface HallPlanService {

    /**
     * Find all hall plans stored in the system.
     *
     * @return Returns a list of hall plan entities
     */
    List<HallPlan> findAll();

    HallPlan createHallplan(HallPlanDto hallPlanDto) throws ValidationException;

    DetailedHallPlanDto getHallPlanById(Long id);

    void deleteHallPlanById(Long id);

    HallPlanDto updateHallPlanById(Long id, HallPlanDto hallPlanDto);

    HallPlanSection createSection(HallPlanSectionDto sectionDto);

    HallPlanSection updateSection(Long id, HallPlanSectionDto sectionDto);

    void deleteSection(Long id);

    HallPlanSection getSection(Long id);

    List<HallPlanSection> getAllSections();

    List<HallPlanSection> findAllByHallPlanId(Long id);
}
