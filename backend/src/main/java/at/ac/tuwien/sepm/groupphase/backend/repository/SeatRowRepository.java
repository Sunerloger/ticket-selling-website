package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.entity.HallPlan;
import at.ac.tuwien.sepm.groupphase.backend.entity.SeatRow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SeatRowRepository extends JpaRepository<SeatRow, Long> {
    @Query("SELECT sr FROM SeatRow sr JOIN FETCH sr.hallPlan")
    List<SeatRow> findAllSeatRowsWithHallPlans();

    Optional<SeatRow> findByRowNrAndHallPlan(Long rowNr, HallPlan hallPlan);
}

