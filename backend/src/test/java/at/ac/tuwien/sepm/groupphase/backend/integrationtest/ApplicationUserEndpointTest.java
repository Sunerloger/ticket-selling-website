package at.ac.tuwien.sepm.groupphase.backend.integrationtest;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.UserCreateDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.UserDeleteDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.UserDetailDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.UserLoginDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.UserMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.ApplicationUser;
import at.ac.tuwien.sepm.groupphase.backend.repository.ApplicationUserRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.xml.bind.ValidationException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class ApplicationUserEndpointTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    ApplicationUserRepository applicationUserRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    PasswordEncoder passwordEncoder;


    static final String BASE_PATH = "/api/v1/user";

    private final UserCreateDto testUser1 = new UserCreateDto(-1000L, "John@email.com", "John", "Doe", LocalDate.parse("1988-12-12"),
        "Teststreet 44/7", 1010L, "Vienna", "Password123%", false, false);

    private final UserCreateDto testUser2 = new UserCreateDto(-1000L, "James@email.com", "James", "Doe", LocalDate.parse("1988-12-12"),
        "Teststreet 44/7", 1010L, "Vienna", "Password123%", false, true);

    //Initial Setup for Tests
    @BeforeEach
    @Transactional
    public void beforeEach() throws ValidationException {

        // Delete existing users
        applicationUserRepository.deleteAll();
        userService.register(userMapper.userCreateDtoToEntity(testUser1));
        userService.register(userMapper.userCreateDtoToEntity(testUser2));
    }


    @Test
    @Transactional
    public void givenUserDetailDtoAndAuthorizationToken_whenUpdateUser_thenUpdateUser() throws Exception {
        UserDetailDto userDetailDto = new UserDetailDto(
            1L, false, "John@email.com", "Jonathan", "Frakes", LocalDate.parse("1988-12-12"),
            "Blumenstraße 1-2/44", 10000L, "Vienna", "Password123%", false
        );
        //Receive the token for the user before performing edit
        UserLoginDto userLoginDto = new UserLoginDto();
        userLoginDto.setEmail("John@email.com");
        userLoginDto.setPassword("Password123%");
        String token = userService.login(userLoginDto);

        MvcResult mvcResult = this.mockMvc.perform(put(BASE_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, token)
                .content(objectMapper.writeValueAsString(userDetailDto)))
            .andExpect(status().isOk())
            .andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();

        assertAll(
            () -> assertEquals(HttpStatus.OK.value(), response.getStatus())
        );
    }

    @Test
    @Transactional
    public void givenUserDetailDtoAndAuthorizationTokenButWrongPassword_whenUpdateUser_isForbidden() throws Exception {
        UserDetailDto userDetailDto = new UserDetailDto(
            1L, false, "John@email.com", "Jonathan", "Frakes", LocalDate.parse("1988-12-12"),
            "Blumenstraße 1-2/44", 10000L, "Vienna", "ThisIsAWrongPassword123%", false
        );
        //Receive the token for the user before performing edit
        UserLoginDto userLoginDto = new UserLoginDto();
        userLoginDto.setEmail("John@email.com");
        userLoginDto.setPassword("Password123%");
        String token = userService.login(userLoginDto);

        mockMvc.perform(put(BASE_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, token)
                .content(objectMapper.writeValueAsString(userDetailDto)))
            .andExpect(status().isForbidden());
    }


    @Test
    @Transactional
    public void givenUserDetailDtoWithWrongFirstNameAndAuthorizationToken_Then_ValidationException() throws Exception {
        UserDetailDto userDetailDto = new UserDetailDto(
            1L, false, "John@email.com", "Jonathan123", "Frakes", LocalDate.parse("1988-12-12"),
            "Blumenstraße 1-2/44", 10000L, "Vienna", "Password123%", false
        );
        //Receive the token for the user before performing edit
        UserLoginDto userLoginDto = new UserLoginDto();
        userLoginDto.setEmail("John@email.com");
        userLoginDto.setPassword("Password123%");
        String token = userService.login(userLoginDto);

        mockMvc.perform(put(BASE_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, token)
                .content(objectMapper.writeValueAsString(userDetailDto)))
            .andExpect(status().isUnprocessableEntity())
            .andExpect(jsonPath("$.errors[0].message").value("First name must contain only letters"));
    }

    @Test
    @Transactional
    public void givenUserDetailDtoWithWrongLastNameAndAuthorizationToken_Then_ValidationException() throws Exception {
        UserDetailDto userDetailDto = new UserDetailDto(
            1L, false, "John@email.com", "Jonathan", "Frakes123", LocalDate.parse("1988-12-12"),
            "Blumenstraße 1-2/44", 10000L, "Vienna", "Password123%", false
        );
        //Receive the token for the user before performing edit
        UserLoginDto userLoginDto = new UserLoginDto();
        userLoginDto.setEmail("John@email.com");
        userLoginDto.setPassword("Password123%");
        String token = userService.login(userLoginDto);

        mockMvc.perform(put(BASE_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, token)
                .content(objectMapper.writeValueAsString(userDetailDto)))
            .andExpect(status().isUnprocessableEntity())
            .andExpect(jsonPath("$.errors[0].message").value("Last name must contain only letters"));
    }

    @Test
    @Transactional
    public void givenUserDetailDtoWithWrongAreaCodeAndAuthorizationToken_Then_ValidationException() throws Exception {
        UserDetailDto userDetailDto = new UserDetailDto(
            1L, false, "John@email.com", "Jonathan", "Frakes", LocalDate.parse("1988-12-12"),
            "Blumenstraße 1-2/44", -10000L, "Vienna", "Password123%", false
        );
        //Receive the token for the user before performing edit
        UserLoginDto userLoginDto = new UserLoginDto();
        userLoginDto.setEmail("John@email.com");
        userLoginDto.setPassword("Password123%");
        String token = userService.login(userLoginDto);

        mockMvc.perform(put(BASE_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, token)
                .content(objectMapper.writeValueAsString(userDetailDto)))
            .andExpect(status().isUnprocessableEntity())
            .andExpect(jsonPath("$.errors[0].message").value("Area Code must be a positive number"));
    }

    @Test
    @Transactional
    public void givenUserDetailDtoWithWrongCityNameAndAuthorizationToken_Then_ValidationException() throws Exception {
        UserDetailDto userDetailDto = new UserDetailDto(
            1L, false, "John@email.com", "Jonathan", "Frakes", LocalDate.parse("1988-12-12"),
            "Blumenstraße 1-2/44", 10000L, "Vienna123", "Password123%", false
        );
        //Receive the token for the user before performing edit
        UserLoginDto userLoginDto = new UserLoginDto();
        userLoginDto.setEmail("John@email.com");
        userLoginDto.setPassword("Password123%");
        String token = userService.login(userLoginDto);

        mockMvc.perform(put(BASE_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, token)
                .content(objectMapper.writeValueAsString(userDetailDto)))
            .andExpect(status().isUnprocessableEntity())
            .andExpect(jsonPath("$.errors[0].message").value("City name must contain only letters"));
    }

    @Test
    @Transactional
    public void givenUserDetailDtoWithBirthdateInTheFutureAndAuthorizationToken_Then_ValidationException() throws Exception {
        UserDetailDto userDetailDto = new UserDetailDto(
            1L, false, "John@email.com", "Jonathan", "Frakes", LocalDate.parse("2030-12-12"),
            "Blumenstraße 1-2/44", 10000L, "Vienna", "Password123%", false
        );
        //Receive the token for the user before performing edit
        UserLoginDto userLoginDto = new UserLoginDto();
        userLoginDto.setEmail("John@email.com");
        userLoginDto.setPassword("Password123%");
        String token = userService.login(userLoginDto);

        mockMvc.perform(put(BASE_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, token)
                .content(objectMapper.writeValueAsString(userDetailDto)))
            .andExpect(status().isUnprocessableEntity())
            .andExpect(jsonPath("$.errors[0].message").value("Birthdate must be in the past"));
    }

    @Test
    @Transactional
    public void giveUserDeleteDtoWithExistingUser_WhenDelete_ThenDeleteUser() throws Exception {

        UserDeleteDto userDeleteDto = new UserDeleteDto(testUser1.id(), testUser1.email(), testUser1.password());
        //Receive the token for the user before performing delete
        UserLoginDto userLoginDto = new UserLoginDto();
        userLoginDto.setEmail(testUser1.email());
        userLoginDto.setPassword(testUser1.password());
        String token = userService.login(userLoginDto);

        mockMvc.perform(delete(BASE_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, token)
                .content(objectMapper.writeValueAsString(userDeleteDto)))
            .andExpect(status().isOk());

        // Assert that the user no longer exists in the repository
        assertFalse(applicationUserRepository.existsById(testUser1.id()));
    }

}
