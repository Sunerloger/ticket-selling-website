package at.ac.tuwien.sepm.groupphase.backend.repository;
import at.ac.tuwien.sepm.groupphase.backend.entity.HallPlanSection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HallPlanSectionRepository extends JpaRepository<HallPlanSection, Long> {

    List<HallPlanSection> findByHallPlanId(Long hallPlanId);
}
