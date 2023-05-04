package at.ac.tuwien.sepm.groupphase.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "seatrow")
public class SeatRow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "rowNr")
    private Long rowNr;
    @ManyToOne
    @JoinColumn(name = "hallplan_id")
    private HallPlan hallPlan;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRowNr() {
        return rowNr;
    }

    public void setRowNr(Long rowNr) {
        this.rowNr = rowNr;
    }

    public HallPlan getHallPlan() {
        return hallPlan;
    }

    public void setHallPlan(HallPlan hallPlan) {
        this.hallPlan = hallPlan;
    }
}
