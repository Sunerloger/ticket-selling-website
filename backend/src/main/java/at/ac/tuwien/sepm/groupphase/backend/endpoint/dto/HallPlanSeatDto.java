package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;


import at.ac.tuwien.sepm.groupphase.backend.entity.HallPlanSection;
import jakarta.validation.constraints.NotNull;

public class HallPlanSeatDto {

    private Long id;
    private Long hallPlanId;

    @NotNull(message = "status must be specified")
    private String status;

    @NotNull(message = "type must be specified")
    private String type;

    @NotNull(message = "capacity must be specified")
    private Long capacity;

    @NotNull(message = "seatNr must be specified")
    private Long seatNr;
    @NotNull(message = "section_id must be specified")
    private HallPlanSectionDto section;

    private Long seatrowId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getCapacity() {
        return capacity;
    }

    public void setCapacity(Long capacity) {
        this.capacity = capacity;
    }

    public Long getSeatNr() {
        return seatNr;
    }

    public void setSeatNr(Long seatNr) {
        this.seatNr = seatNr;
    }

    public Long getHallPlanId() {
        return hallPlanId;
    }

    public void setHallPlanId(Long hallPlanId) {
        this.hallPlanId = hallPlanId;
    }

    public HallPlanSectionDto getSection() {
        return section;
    }

    public void setSection(HallPlanSectionDto section) {
        this.section = section;
    }

    public Long getSeatrowId() {
        return seatrowId;
    }

    public void setSeatrowId(Long seatrowId) {
        this.seatrowId = seatrowId;
    }

    public static final class SeatDtoBuilder {

        private Long id;
        private String status;
        private String type;
        private Long capacity;
        private Long seatNr;
        private Long sectionId;

        private SeatDtoBuilder() {
        }

        public static SeatDtoBuilder aSeatDto() {
            return new SeatDtoBuilder();
        }

        public SeatDtoBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public SeatDtoBuilder withStatus(String status) {
            this.status = status;
            return this;
        }

        public SeatDtoBuilder withType(String type) {
            this.type = type;
            return this;
        }

        public SeatDtoBuilder withCapacity(Long capacity) {
            this.capacity = capacity;
            return this;
        }

        public SeatDtoBuilder withSeatNr(Long seatNr) {
            this.seatNr = seatNr;
            return this;
        }

        public SeatDtoBuilder withSectionId(Long sectionId) {
            this.sectionId = sectionId;
            return this;
        }

        public HallPlanSeatDto build() {
            HallPlanSeatDto seatDto = new HallPlanSeatDto();
            seatDto.setId(id);
            seatDto.setStatus(status);
            seatDto.setType(type);
            seatDto.setCapacity(capacity);
            seatDto.setSeatNr(seatNr);
            //seatDto.setHa
            return seatDto;
        }
    }
}

