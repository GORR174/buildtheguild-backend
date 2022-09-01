package net.catstack.buildtheguild.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.catstack.buildtheguild.dto.request.LoginUserRequestDto;
import net.catstack.buildtheguild.dto.request.RegisterUserRequestDto;
import net.catstack.buildtheguild.dto.response.LoginUserResponseDto;
import net.catstack.buildtheguild.dto.response.RegisterUserResponseDto;
import net.catstack.buildtheguild.service.AuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public LoginUserResponseDto loginUser(@Valid @RequestBody final LoginUserRequestDto requestDto) {
        return authService.loginUser(requestDto);
    }

    @PostMapping("/register")
    public RegisterUserResponseDto registerUser(@Valid @RequestBody final RegisterUserRequestDto requestDto) {
        return authService.registerUser(requestDto);
    }
}
