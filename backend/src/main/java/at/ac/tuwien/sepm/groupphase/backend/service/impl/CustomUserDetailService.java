package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.ResetPasswordUser;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.UserLoginDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.ApplicationUser;
import at.ac.tuwien.sepm.groupphase.backend.entity.PasswordResetToken;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.repository.ApplicationUserRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.TokenRepository;
import at.ac.tuwien.sepm.groupphase.backend.security.JwtAuthorizationFilter;
import at.ac.tuwien.sepm.groupphase.backend.security.JwtTokenizer;
import at.ac.tuwien.sepm.groupphase.backend.service.UserService;

import jakarta.xml.bind.ValidationException;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
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
    private final ApplicationUserRepository applicationUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtAuthorizationFilter jwtAuthorizationFilter;
    private final JwtTokenizer jwtTokenizer;
    private final TokenRepository tokenRepository;

    @Autowired
    public CustomUserDetailService(ApplicationUserRepository applicationUserRepository, PasswordEncoder passwordEncoder,
                                   JwtTokenizer jwtTokenizer, JwtAuthorizationFilter jwtAuthorizationFilter, TokenRepository tokenRepository) {
        this.applicationUserRepository = applicationUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtAuthorizationFilter = jwtAuthorizationFilter;
        this.jwtTokenizer = jwtTokenizer;
        this.tokenRepository = tokenRepository;
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

    @Override
    public ApplicationUser register(ApplicationUser applicationUser) throws ValidationException {
        String encodedPassword = passwordEncoder.encode(applicationUser.getPassword());
        applicationUser.setPassword(encodedPassword);
        checkForExistingUserByEmail(applicationUser.getEmail());
        return applicationUserRepository.save(applicationUser);
    }

    @Override
    public ApplicationUser edit(ApplicationUser applicationUser, String token) {
        UserDetails currentUser = loadUserByUsername(applicationUser.getEmail());
        if (passwordEncoder.matches(applicationUser.getPassword(), currentUser.getPassword())) {
            String encodedPassword = passwordEncoder.encode(applicationUser.getPassword());
            applicationUser.setPassword(encodedPassword);
            return applicationUserRepository.save(applicationUser);
        }
        throw new BadCredentialsException("Password was wrong!");
    }

    @Override
    public ApplicationUser getUser(String token) {
        String email = jwtAuthorizationFilter.getUsernameFromToken(token);
        return applicationUserRepository.findUserByEmail(email);
    }

    @Override
    public void delete(Long id, String email, String password) {
        UserDetails userDetails = loadUserByUsername(email);
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Password is incorrect");
        }
        applicationUserRepository.deleteById(id);

    }

    @Override
    public void block(ApplicationUser applicationUser) {
        applicationUserRepository.updateIsLocked(applicationUser.getEmail(), applicationUser.getLocked());
    }

    @Override
    public List<ApplicationUser> getBlockedUsers(ApplicationUser applicationUser, String token, int pageIndex) {
        ApplicationUser admin = getUser(token);
        Pageable pageable = PageRequest.of(pageIndex, 20, Sort.by("email").ascending());
        if (applicationUser.getLocked().equals(Boolean.TRUE)) {
            return applicationUserRepository.findUserByIsLockedIsTrueAndEmail(applicationUser.getEmail(), admin.getEmail(), pageable);
        } else {
            return applicationUserRepository.findUserByIsLockedIsFalseAndEmail(applicationUser.getEmail(), admin.getEmail(), pageable);
        }

    }

    @Override
    public Long getUserIdFromToken(String token) {
        ApplicationUser user = this.getUser(token);
        return user == null ? null : user.getId();
    }

    @Override
    public ApplicationUser getUserById(Long id) {
        return applicationUserRepository.getApplicationUserById(id);
    }

    @Override
    public void resetPassword(ResetPasswordUser user) {
        PasswordResetToken actualToken = tokenRepository.getTokenByEmail(user.email());

        if (!actualToken.getToken().equals(user.token())) {
            throw new InsufficientAuthenticationException("Token are not equal");
        }

        String encodedPassword = passwordEncoder.encode(user.newPassword());
        applicationUserRepository.updatePassword(user.email(), encodedPassword);

    }


}
