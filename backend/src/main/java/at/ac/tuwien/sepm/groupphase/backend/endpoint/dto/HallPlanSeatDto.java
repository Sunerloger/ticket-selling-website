package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import at.ac.tuwien.sepm.groupphase.backend.type.HallPlanSeatStatus;

public class HallPlanSeatDto {
    private Long id;
    private HallPlanSeatStatus status;
    private Long rowNr;
    private Long seatNr;
    private Long section_id;
}
