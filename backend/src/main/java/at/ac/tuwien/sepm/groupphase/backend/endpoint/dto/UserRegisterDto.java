package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;

public record UserRegisterDto(
    Long id,
    @NotEmpty
    @Column(nullable = false)
    @Pattern(regexp = "[ÄÖÜA-Zäöüa-z0-9@]*")
    String email,
    @NotEmpty
    @Column(nullable = false)
    @Pattern(regexp = "[ÄÖÜA-Zäöüa-z]*")
    String firstName,
    @NotEmpty
    @Column(nullable = false)
    @Pattern(regexp = "[ÄÖÜA-Zäöüa-z]*")
    String lastName,
    @NotEmpty
    @Column(nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate birthdate,
    @NotEmpty
    @Column(nullable = false)
    @Pattern(regexp = "[ÄÖÜA-Zäöüa-z0-9]*")
    String address,
    @NotEmpty
    @Column(nullable = false)
    @Pattern(regexp = "[0-9]{4}")
    Long areaCode,
    @NotEmpty
    @Column(nullable = false)
    @Pattern(regexp = "[ÄÖÜA-Zäöüa-z]*")
    String cityName,
    @NotEmpty
    @Column(nullable = false)
    @Pattern(regexp = "[ÄÖÜA-Zäöüa-z0-9]*")
    String password) {
}

