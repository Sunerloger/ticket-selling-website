package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public record UserRegisterDto(
    Long id,
    String email,
    String firstName,
    String lastName,
    @JsonFormat(pattern="yyyy-MM-dd")
    LocalDate birthdate,
    String address,
    Long areaCode,
    String cityName,
    String password) {
}
/*
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private LocalDate birthdate;
    private String address;
    private Long areaCode;
    private String cityName;
    private String password;

    public UserRegisterDto(Long id, String email, String firstName, String lastName, LocalDate birthdate, String address, Long areaCode, String cityName,
                           String password) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthdate = birthdate;
        this.address = address;
        this.areaCode = areaCode;
        this.cityName = cityName;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


 */

