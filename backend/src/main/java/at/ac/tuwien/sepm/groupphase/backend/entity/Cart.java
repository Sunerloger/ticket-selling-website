package at.ac.tuwien.sepm.groupphase.backend.entity;

import jakarta.persistence.*;
@Entity
@Table(name = "cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private Long seatId;
    public Cart(Long userId, Long seatId) {
        this.seatId = seatId;
        this.userId = userId;
    }
    public Cart() {

    }
    public Long getSeatId() {
        return seatId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setSeatId(Long seatId) {
        this.seatId = seatId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }
}
