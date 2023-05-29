package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.time.LocalDate;

public record UserRegisterDto(
    Long id,

    @Column(unique = true, nullable = false)
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
    @Positive
    Long areaCode,
    @Column(nullable = false)
    String cityName,
    @Column(nullable = false)
    String password,

    Boolean admin,

    Boolean isLocked


) {
}

