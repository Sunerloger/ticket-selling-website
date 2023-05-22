package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;

import java.time.LocalDate;

public record UserDetailDto(
    Long id,

    Boolean admin,
    @Column(nullable = false, unique = true)
    String email,

    @Column(nullable = false)
    String firstName,

    @Column(nullable = false)
    String lastName,

    @Column(nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate birthdate,

    @Column(nullable = false)
    String address,

    @Column(nullable = false)
    Long areaCode,

    @Column(nullable = false)
    String cityName,

    @Column(nullable = false)
    String password
) {
}
