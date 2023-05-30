package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;

public record UserUnBlockDto(
    @Column(nullable = false, unique = true)
    String email,

    @Column(nullable = false)
    boolean isLocked
) {

}
