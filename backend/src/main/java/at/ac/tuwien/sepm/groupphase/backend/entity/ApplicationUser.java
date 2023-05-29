package at.ac.tuwien.sepm.groupphase.backend.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;

//TODO: replace this class with a correct ApplicationUser Entity implementation
@Entity
@Table(name = "applicationuser") /*, uniqueConstraints = {
    @UniqueConstraint(columnNames = {"email"})
}*/
public class ApplicationUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    @Pattern(regexp = "[ÄÖÜA-Zäöüa-z]*")
    private String firstName;

    @Pattern(regexp = "[ÄÖÜA-Zäöüa-z]*")
    private String lastName;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthdate;

    private String address;

    private Long areaCode;

    @Pattern(regexp = "[ÄÖÜA-Zäöüa-z]*")
    private String cityName;

    private String password;

    private Boolean admin = false;

    private Boolean isLocked = false;

    public ApplicationUser() {
    }


    public ApplicationUser(String email, String password, Boolean admin) {
        this.email = email;
        this.password = password;
        this.admin = admin;
    }


    public ApplicationUser(String email, String firstName, String lastName, LocalDate birthdate, String address, Long areaCode, String cityName,
                           String password, Boolean admin, Boolean isLocked) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthdate = birthdate;
        this.address = address;
        this.areaCode = areaCode;
        this.cityName = cityName;
        this.password = password;
        this.admin = admin;
        this.isLocked = isLocked;
    }

    public ApplicationUser(String email, Boolean isLocked) {
        this.email = email;
        this.isLocked = isLocked;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(Long areaCode) {
        this.areaCode = areaCode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Boolean getLocked() {
        return isLocked;
    }

    public void setLocked(Boolean locked) {
        isLocked = locked;
    }

}
