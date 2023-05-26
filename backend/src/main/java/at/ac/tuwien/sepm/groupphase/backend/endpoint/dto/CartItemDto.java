package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public class CartItemDto {
    private SeatDto seat;
    private EventDetailDto event;

    public CartItemDto() {
    }

    public CartItemDto(SeatDto seat, EventDetailDto event) {
        this.seat = seat;
        this.event = event;
    }

    public EventDetailDto getEvent() {
        return event;
    }

    public void setEvent(EventDetailDto event) {
        this.event = event;
    }

    public SeatDto getSeat() {
        return seat;
    }

    public void setSeat(SeatDto seat) {
        this.seat = seat;
    }
}
