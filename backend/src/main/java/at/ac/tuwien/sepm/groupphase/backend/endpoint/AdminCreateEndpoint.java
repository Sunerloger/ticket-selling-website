package at.ac.tuwien.sepm.groupphase.backend.endpoint;


import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.UserCreateDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.UserUnBlockDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.UserMapper;
import at.ac.tuwien.sepm.groupphase.backend.service.PasswordResetService;
import at.ac.tuwien.sepm.groupphase.backend.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.xml.bind.ValidationException;
import org.apache.coyote.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.lang.invoke.MethodHandles;
import java.util.List;

@RestController
@RequestMapping(value = AdminCreateEndpoint.BASE_PATH)
public class AdminCreateEndpoint {

    static final String BASE_PATH = "/api/v1/admin";

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final UserService userService;

    private final UserMapper userMapper;
    private final PasswordResetService passwordResetService;

    @Autowired
    public AdminCreateEndpoint(UserService userService, UserMapper userMapper, PasswordResetService passwordResetService) {
        this.userMapper = userMapper;
        this.userService = userService;
        this.passwordResetService = passwordResetService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    @Secured({"ROLE_ADMIN"})
    @Operation(summary = "Create a new user", security = @SecurityRequirement(name = "apiKey"))
    public UserCreateDto post(@RequestBody UserCreateDto userCreateDto) {
        LOGGER.info("POST: {}", userCreateDto);
        try {
            return userMapper.entityToUserCreateDto(userService.register(userMapper.userCreateDtoToEntity(userCreateDto)));
        } catch (ValidationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @Secured({"ROLE_ADMIN"})
    @PostMapping("send-reset-mail")
    public void resetPassword(@RequestBody String email) {
        LOGGER.info("RESETING PASSWORD: {}", email);
        passwordResetService.initiatePasswordReset(email);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping
    @Secured("ROLE_ADMIN")
    @Operation(summary = "Block/Unblock a user", security = @SecurityRequirement(name = "apiKey"))
    public void put(@RequestBody UserUnBlockDto userUnBlockDto) {
        LOGGER.info("Block user " + BASE_PATH + "user: {}", userUnBlockDto.email());
        userService.block(userMapper.userUnBlockDtoToEntity(userUnBlockDto));
    }

    @GetMapping
    @Secured("ROLE_ADMIN")
    public List<UserUnBlockDto> getBlockedUsers(UserUnBlockDto userUnBlockDto, @RequestParam(defaultValue = "0") int pageIndex, @RequestParam(value = "token") String token) {
        LOGGER.info("Get blocked users " + BASE_PATH);
        return userMapper.entityToStreamUserUnBlockDto(userService.getBlockedUsers(userMapper.userUnBlockDtoToEntity(userUnBlockDto), token, pageIndex));
    }


}
