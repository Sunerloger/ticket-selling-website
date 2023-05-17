package at.ac.tuwien.sepm.groupphase.backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import org.h2.engine.User;
import org.springframework.data.annotation.Reference;

@Entity
@Table(name = "cart")
public class Cart {
    private Long userId;
    @Id
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
}
