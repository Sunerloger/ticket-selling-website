package at.ac.tuwien.sepm.groupphase.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.List;

@Entity
@Table(name = "hallplan")
public class HallPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name="hallplan_id")
    private List<SeatRow> seatRows;

    public List<SeatRow> getSeatRows() {
        return seatRows;
    }

    public void setSeatRows(List<SeatRow> seatRows) {
        this.seatRows = seatRows;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static final class HallPlanBuilder {
        private String name;
        private String description;

        public static HallPlanBuilder aHallPlan() {
            return new HallPlanBuilder();
        }

        public HallPlanBuilder withName() {
            this.name = name;
            return this;
        }

        public HallPlanBuilder withDescription() {
            this.description = description;
            return this;
        }

        public HallPlan build() {
            HallPlan hallPlan = new HallPlan();
            hallPlan.setName(name);
            hallPlan.setDescription(description);
            return hallPlan;
        }


    }
}
