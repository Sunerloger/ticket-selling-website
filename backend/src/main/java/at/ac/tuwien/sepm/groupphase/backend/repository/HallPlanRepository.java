package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.HallPlanDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.HallPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface HallPlanRepository extends JpaRepository<HallPlan, Long> {

    @Query("SELECT c FROM HallPlan c")
    List<HallPlan> findAllHallPlans();

    HallPlan save(HallPlan hallPlan);

    @Transactional
    void deleteById(Long id);
}
