package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import at.ac.tuwien.sepm.groupphase.backend.entity.HallPlan;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class SeatRowDto {

    private Long id;

    @NotNull(message = "rowNr must be specified")
    private Long rowNr;

    private HallPlanDto hallPlan;

    private List<HallPlanSeatDto> seats;

    public List<HallPlanSeatDto> getSeats() {
        return seats;
    }

    public void setSeats(List<HallPlanSeatDto> seats) {
        this.seats = seats;
    }

    public HallPlanDto getHallPlan() {
        return hallPlan;
    }

    public void setHallPlan(HallPlanDto hallPlan) {
        this.hallPlan = hallPlan;
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

    public static final class SeatRowDtoBuilder {

        private Long id;
        private Long rowNr;
        private HallPlanDto hallPlan;

        private SeatRowDtoBuilder() {
        }

        public static SeatRowDtoBuilder aSeatRowDto() {
            return new SeatRowDtoBuilder();
        }

        public SeatRowDtoBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public SeatRowDtoBuilder withRowNr(Long rowNr) {
            this.rowNr = rowNr;
            return this;
        }

        public SeatRowDtoBuilder withHallPlanId(HallPlanDto hallPlan) {
            this.hallPlan = hallPlan;
            return this;
        }

        public SeatRowDto build() {
            SeatRowDto seatRowDto = new SeatRowDto();
            seatRowDto.setId(id);
            seatRowDto.setRowNr(rowNr);
            seatRowDto.setHallPlan(hallPlan);
            return seatRowDto;
        }
    }
}
