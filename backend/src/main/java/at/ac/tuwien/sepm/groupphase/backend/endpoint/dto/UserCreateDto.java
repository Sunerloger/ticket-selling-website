package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;

public record UserCreateDto(

    Long id,

    @Column(nullable = false, unique = true)
    @Pattern(regexp = "[ÄÖÜA-Zäöüa-z0-9@]*")
    String email,
    @NotEmpty
    @Column(nullable = false)
    @Pattern(regexp = "[ÄÖÜA-Zäöüa-z]*")
    String firstName,

    @Column(nullable = false)
    @Pattern(regexp = "[ÄÖÜA-Zäöüa-z]*")
    String lastName,

    @Column(nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate birthdate,

    @Column(nullable = false)
    @Pattern(regexp = "[ÄÖÜA-Zäöüa-z0-9]*")
    String address,

    @Column(nullable = false)
    Long areaCode,

    @Column(nullable = false)
    @Pattern(regexp = "[ÄÖÜA-Zäöüa-z]*")
    String cityName,

    @Column(nullable = false)
    @Pattern(regexp = "[ÄÖÜA-Zäöüa-z0-9]*")
    String password,

    Boolean admin) {
}