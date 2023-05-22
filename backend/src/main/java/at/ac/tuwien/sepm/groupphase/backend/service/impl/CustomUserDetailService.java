package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.UserDetailDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.UserLoginDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.ApplicationUser;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.repository.ApplicationUserRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.UserRepository;
import at.ac.tuwien.sepm.groupphase.backend.security.JwtAuthorizationFilter;
import at.ac.tuwien.sepm.groupphase.backend.security.JwtTokenizer;
import at.ac.tuwien.sepm.groupphase.backend.service.UserService;

import jakarta.xml.bind.ValidationException;

import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.util.List;

@Service
public class CustomUserDetailService implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final UserRepository userRepository;
    private final ApplicationUserRepository applicationUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtAuthorizationFilter jwtAuthorizationFilter;
    private final JwtTokenizer jwtTokenizer;

    @Autowired
    public CustomUserDetailService(ApplicationUserRepository applicationUserRepository, UserRepository userRepository, PasswordEncoder passwordEncoder,
                                   JwtTokenizer jwtTokenizer, JwtAuthorizationFilter jwtAuthorizationFilter) {
        this.applicationUserRepository = applicationUserRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtAuthorizationFilter = jwtAuthorizationFilter;
        this.jwtTokenizer = jwtTokenizer;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        LOGGER.debug("Load all user by email");
        try {
            ApplicationUser applicationUser = findApplicationUserByEmail(email);

            List<GrantedAuthority> grantedAuthorities;
            if (applicationUser.getAdmin()) {
                grantedAuthorities = AuthorityUtils.createAuthorityList("ROLE_ADMIN", "ROLE_USER");
            } else {
                grantedAuthorities = AuthorityUtils.createAuthorityList("ROLE_USER");
            }

            return new User(applicationUser.getEmail(), applicationUser.getPassword(), grantedAuthorities);
        } catch (NotFoundException e) {
            throw new UsernameNotFoundException(e.getMessage(), e);
        }
    }

    @Override
    public ApplicationUser findApplicationUserByEmail(String email) {
        LOGGER.debug("Find application user by email");
        ApplicationUser applicationUser = applicationUserRepository.findUserByEmail(email);
        if (applicationUser != null) {
            return applicationUser;
        }
        throw new NotFoundException(String.format("Could not find the user with the email address %s", email));
    }

    @Override
    public void checkForExistingUserByEmail(String email) throws ValidationException {
        LOGGER.debug("Check application user by email");
        ApplicationUser applicationUser = applicationUserRepository.findUserByEmail(email);
        if (applicationUser != null) {
            throw new ValidationException("Email already in use!");
        }
    }

    ;

    @Override
    public String login(UserLoginDto userLoginDto) {
        try {
            UserDetails userDetails = loadUserByUsername(userLoginDto.getEmail());
            if (userDetails != null
                && userDetails.isAccountNonExpired()
                && userDetails.isAccountNonLocked()
                && userDetails.isCredentialsNonExpired()
                && passwordEncoder.matches(userLoginDto.getPassword(), userDetails.getPassword())
            ) {
                List<String> roles = userDetails.getAuthorities()
                    .stream()
                    .map(GrantedAuthority::getAuthority)
                    .toList();
                return jwtTokenizer.getAuthToken(userDetails.getUsername(), roles);
            }
        } catch (UsernameNotFoundException e) {
            throw new BadCredentialsException("Email or password is incorrect or account is locked");
        }
        throw new BadCredentialsException("Email or password is incorrect or account is locked");
    }

    //Validation Exception is thrown if user already exists
    @Override
    public ApplicationUser register(ApplicationUser applicationUser) throws ValidationException {
        String encodedPassword = passwordEncoder.encode(applicationUser.getPassword());
        applicationUser.setPassword(encodedPassword);
        checkForExistingUserByEmail(applicationUser.getEmail());
        return applicationUserRepository.save(applicationUser);
    }

    @Override
    public ApplicationUser edit(ApplicationUser applicationUser, String token) {
        String email = jwtAuthorizationFilter.getUsernameFromToken(token);
        return applicationUserRepository.saveApplicationUserByEmail(applicationUser,email);
    }

    @Override
    public ApplicationUser getUser(String token) {
        String email = jwtAuthorizationFilter.getUsernameFromToken(token);
        System.out.println("TEST");
        return applicationUserRepository.findUserByEmail(email);
    }

}
