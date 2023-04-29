package at.ac.tuwien.sepm.groupphase.backend.entity;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.HallPlanEndpoint;
import jakarta.persistence.*;

@Entity
@Table(name = "section")
public class HallPlanSection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "color", nullable = false)
    private String color;

    @Column(name = "price")
    private Long price;
    @Column(name = "hallplan_id", nullable = false)
    private Long hallPlanId;

    public HallPlanSection() {
    }

    public HallPlanSection(String name, String color, Long price, Long hallPlanId) {
        this.name = name;
        this.color = color;
        this.price = price;
        this.hallPlanId = hallPlanId;
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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getHallPlanId() {
        return hallPlanId;
    }

    public void setHallPlanId(Long hallPlanId) {
        this.hallPlanId = hallPlanId;
    }
}

