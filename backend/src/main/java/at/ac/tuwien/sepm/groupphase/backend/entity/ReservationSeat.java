package at.ac.tuwien.sepm.groupphase.backend.entity;

import jakarta.persistence.*;

@Entity
public class ReservationSeat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long seat;

    public Long getId() {
        return id;
    }

    public Long getSeat() {
        return seat;
    }

    public void setSeat(Long seat) {
        this.seat = seat;
    }
}
