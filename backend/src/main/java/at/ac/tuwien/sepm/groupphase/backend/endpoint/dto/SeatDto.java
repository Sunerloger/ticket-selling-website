package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.hallplan.HallPlanSeatDto;
import at.ac.tuwien.sepm.groupphase.backend.type.HallPlanSeatType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;

public class SeatDto {
    private Long id;
    private Double price;
    @Enumerated(EnumType.STRING)
    @NotNull(message = "type must be specified")
    private HallPlanSeatType type;
    private Long seatNr;
    private String sectionName;
    private Long seatRowNr;

    public SeatDto() {

    }

    public SeatDto(HallPlanSeatDto seatDto, SeatRowDto rowDto) {
        this.id = seatDto.getId();
        this.price = seatDto.getSection().getPrice().doubleValue();
        this.type = seatDto.getType();
        this.seatNr = seatDto.getSeatNr();
        this.sectionName = seatDto.getSection().getName();
        this.seatRowNr = rowDto.getRowNr();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public HallPlanSeatType getType() {
        return type;
    }

    public void setType(HallPlanSeatType type) {
        this.type = type;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public Long getSeatNr() {
        return seatNr;
    }

    public void setSeatNr(Long seatNr) {
        this.seatNr = seatNr;
    }

    public Long getSeatRowNr() {
        return seatRowNr;
    }

    public void setSeatRowNr(Long seatRowNr) {
        this.seatRowNr = seatRowNr;
    }
}
