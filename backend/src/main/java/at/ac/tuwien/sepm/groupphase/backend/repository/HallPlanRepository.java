package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.entity.HallPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface HallPlanRepository extends JpaRepository<HallPlan, Long> {

    @Query("SELECT c FROM HallPlan c")
    List<HallPlan> findAllHallPlans();

    @Query("SELECT h FROM HallPlan h "
        + "LEFT JOIN FETCH h.seatRows r "
        + "WHERE h.id = :hallplanId")
    Optional<HallPlan> findHallPlanById(@Param("hallplanId") Long id);

    @Query("SELECT r FROM SeatRow r "
        + "LEFT JOIN FETCH r.seats "
        + "WHERE r.hallPlanId = :hallplanId")
    Optional<HallPlan> findHallPlanByIdWithSeats(@Param("hallplanId") Long id);

    HallPlan save(HallPlan hallPlan);

    @Transactional
    void deleteById(Long id);
}
