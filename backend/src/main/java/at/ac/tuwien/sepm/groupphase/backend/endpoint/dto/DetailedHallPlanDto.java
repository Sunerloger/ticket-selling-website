package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import at.ac.tuwien.sepm.groupphase.backend.entity.SeatRow;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class DetailedHallPlanDto {
    private Long id;
    @NotNull(message = "name must be specified")
    private String name;
    private String description;

    private List<SeatRowDto> seatRows;

    public List<SeatRowDto> getSeatRows() {
        return seatRows;
    }

    public void setSeatRows(List<SeatRowDto> seatRows) {
        this.seatRows = seatRows;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
