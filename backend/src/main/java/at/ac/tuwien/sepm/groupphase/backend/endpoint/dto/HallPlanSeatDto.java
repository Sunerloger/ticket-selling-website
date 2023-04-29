package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import at.ac.tuwien.sepm.groupphase.backend.type.HallPlanSeatStatus;

public class HallPlanSeatDto {
    private Long id;
    private HallPlanSeatStatus status;
    private Long rowNr;
    private Long seatNr;
    private Long section_id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public HallPlanSeatStatus getStatus() {
        return status;
    }

    public void setStatus(HallPlanSeatStatus status) {
        this.status = status;
    }

    public Long getRowNr() {
        return rowNr;
    }

    public void setRowNr(Long rowNr) {
        this.rowNr = rowNr;
    }

    public Long getSeatNr() {
        return seatNr;
    }

    public void setSeatNr(Long seatNr) {
        this.seatNr = seatNr;
    }

    public Long getSection_id() {
        return section_id;
    }

    public void setSection_id(Long section_id) {
        this.section_id = section_id;
    }

    public static final class HallPlanSeatDtoBuilder {
        private Long id;
        private HallPlanSeatStatus status;
        private Long rowNr;
        private Long seatNr;
        private Long section_id;

        private HallPlanSeatDtoBuilder() {

        }

        public static HallPlanSeatDtoBuilder aHallPlanSeatDto() {
            return new HallPlanSeatDtoBuilder();
        }

        public HallPlanSeatDtoBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public HallPlanSeatDtoBuilder withStatus(HallPlanSeatStatus status) {
            this.status = status;
            return this;
        }

        public HallPlanSeatDtoBuilder withRowNr(Long rowNr) {
            this.rowNr = rowNr;
            return this;
        }

        public HallPlanSeatDtoBuilder withSeatNr(Long seatNr) {
            this.seatNr = seatNr;
            return this;
        }

        public HallPlanSeatDtoBuilder withSectionId(Long section_id) {
            this.section_id = section_id;
            return this;
        }

        public HallPlanSeatDto build() {
            HallPlanSeatDto hallPlanSeatDto = new HallPlanSeatDto();
            hallPlanSeatDto.setId(id);
            hallPlanSeatDto.setStatus(status);
            hallPlanSeatDto.setRowNr(rowNr);
            hallPlanSeatDto.setSeatNr(seatNr);
            hallPlanSeatDto.setSection_id(section_id);
            return hallPlanSeatDto;
        }
    }
}
