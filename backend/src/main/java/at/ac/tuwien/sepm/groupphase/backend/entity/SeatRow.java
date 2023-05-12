package at.ac.tuwien.sepm.groupphase.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.List;

@Entity
@Table(name = "seatrow")
public class SeatRow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "rowNr")
    private Long rowNr;

    @Column(name = "hallplan_id")
    private Long hallPlanId;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "seatrow_id")
    private List<HallPlanSeat> seats;

    public List<HallPlanSeat> getSeats() {
        return seats;
    }

    public void setSeats(List<HallPlanSeat> seats) {
        this.seats = seats;
    }

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

    public Long getHallPlanId() {
        return hallPlanId;
    }

    public void setHallPlanId(Long hallPlanId) {
        this.hallPlanId = hallPlanId;
    }

}
