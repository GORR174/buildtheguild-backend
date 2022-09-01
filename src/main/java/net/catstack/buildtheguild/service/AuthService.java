package net.catstack.buildtheguild.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.catstack.buildtheguild.dto.request.LoginUserRequestDto;
import net.catstack.buildtheguild.dto.request.RegisterUserRequestDto;
import net.catstack.buildtheguild.dto.response.LoginUserResponseDto;
import net.catstack.buildtheguild.dto.response.RegisterUserResponseDto;
import net.catstack.buildtheguild.enums.UserRole;
import net.catstack.buildtheguild.model.Role;
import net.catstack.buildtheguild.model.User;
import net.catstack.buildtheguild.repository.RoleRepository;
import net.catstack.buildtheguild.repository.UserRepository;
import net.catstack.buildtheguild.security.JwtUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    private final PasswordEncoder passwordEncoder;

    public RegisterUserResponseDto registerUser(final RegisterUserRequestDto requestDto) {
        log.info("Starting user {} register...", requestDto.getUsername());

        final var responseDto = new RegisterUserResponseDto();

        if (userRepository.existsByUsername(requestDto.getUsername())) {
            responseDto.setMessage("Username is already taken!");
            return responseDto;
        }

        if (userRepository.existsByEmail(requestDto.getEmail())) {
            responseDto.setMessage("Email is already in use!");
            return responseDto;
        }

        final var user = new User();
        user.setUsername(requestDto.getUsername());
        user.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        user.setEmail(requestDto.getEmail());

        final var roles = new HashSet<Role>();
        final var userRole = roleRepository.findByName(UserRole.ROLE_USER);
        roles.add(userRole);

        user.setRoles(roles);
        userRepository.save(user);

        log.info("User " + user.getUsername() + " successfully registered!");
        responseDto.setMessage("Successful");

        return responseDto;
    }

    public LoginUserResponseDto loginUser(final LoginUserRequestDto requestDto) {
        log.info("Starting user {} login...", requestDto.getUsername());

        var responseDto = new LoginUserResponseDto();

        var authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(requestDto.getUsername(), requestDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        var jwt = jwtUtils.generateJwtToken(authentication);

        responseDto.setToken(jwt);

        log.info("User {} logged in", requestDto.getUsername());

        return responseDto;
    }
}
