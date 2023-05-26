package at.ac.tuwien.sepm.groupphase.backend.entity;

import jakarta.persistence.*;

@Entity
public class ReservationSeat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long seatId;
    public ReservationSeat(){

    }
    public ReservationSeat(Long seatId){
        this.seatId = seatId;
    }
    public Long getId() {
        return id;
    }
    public Long getSeatId() {
        return seatId;
    }
    public void setSeatId(Long seat) {
        this.seatId = seat;
    }
}
