package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record EventDetailDto (
    Long id,
    String title,
    LocalDate date,
    LocalTime startTime,
    String cityname,
    int areaCode,
    double duration,
    String category,
    String address,
    String description
) {

}
