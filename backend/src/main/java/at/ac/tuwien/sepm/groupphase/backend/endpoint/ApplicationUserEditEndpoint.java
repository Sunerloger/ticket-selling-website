package at.ac.tuwien.sepm.groupphase.backend.endpoint;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.ResetPasswordUser;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.UserDeleteDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.UserDetailDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.UserMapper;
import at.ac.tuwien.sepm.groupphase.backend.service.PasswordResetService;
import at.ac.tuwien.sepm.groupphase.backend.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


import java.lang.invoke.MethodHandles;

@RestController
@RequestMapping(value = ApplicationUserEditEndpoint.BASE_PATH)
public class ApplicationUserEditEndpoint {


    //TODO: change URI
    static final String BASE_PATH = "/api/v1/edit";

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final UserService userService;
    private final UserMapper userMapper;
    private final PasswordResetService passwordResetService;

    @Autowired
    public ApplicationUserEditEndpoint(UserService userService, UserMapper userMapper, PasswordResetService passwordResetService) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.passwordResetService = passwordResetService;
    }

    @DeleteMapping
    @PermitAll
    @ResponseStatus(HttpStatus.OK)
    public void delete(UserDeleteDto userDeleteDto) {
        LOGGER.info("DELETE " + BASE_PATH);
        userService.delete(userDeleteDto.id(), userDeleteDto.email(), userDeleteDto.password());
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping
    @PermitAll
    @Operation(summary = "Edit a user")
    public void update(@Valid @RequestBody UserDetailDto userDetailDto, @RequestParam(value = "token") String token) {
        LOGGER.info("EDIT USER " + BASE_PATH + "with TOKEN " + token, userDetailDto);
        userMapper.entityToUserDetailDto(userService.edit(userMapper.userDetailDtoToEntity(userDetailDto), token));
    }

    @GetMapping
    @PermitAll
    public UserDetailDto getUser(@RequestHeader("Authorization") String token) {
        LOGGER.info("GET USER " + BASE_PATH + "with TOKEN" + token);
        return userMapper.entityToUserDetailDto(userService.getUser(token));
    }


    @ResponseStatus(HttpStatus.OK)
    @PermitAll
    @PostMapping("/reset-password")
    public void resetPassword(@RequestBody ResetPasswordUser user) {
        LOGGER.info("Reseting password for user {}", user);
        userService.resetPassword(user);
    }

    @ResponseStatus(HttpStatus.OK)
    @PermitAll
    @PostMapping("send-reset-mail")
    public void sendResetMail(@RequestBody String email) {
        LOGGER.info("Sending Reset Mail for user {}", email);
        passwordResetService.initiatePasswordReset(email);
    }
}
