package dev.shaz.userservice.controllers;

import dev.shaz.userservice.dtos.*;
import dev.shaz.userservice.models.SessionStatus;
import dev.shaz.userservice.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private AuthService authService;

    @Autowired
    public AuthController(AuthService authService){
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<UserDto> logIn(@RequestBody LoginRequestDto requestDto){
        return authService.logIn(requestDto.getEmail(), requestDto.getPassword());
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logOut(@RequestBody LogoutRequestDto requestDto){
        authService.logOut(requestDto.getUserId(), requestDto.getToken());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/signup")
    public ResponseEntity<UserDto> signUp(@RequestBody SignUpRequestDto requestDto){
        UserDto userDto = authService.signUp(requestDto.getEmail(), requestDto.getPassword());
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @PostMapping("/validate")
    public ResponseEntity<SessionStatus> validate(@RequestBody ValidateTokenRequestDto requestDto){
        SessionStatus status = authService.validate(requestDto.getUserId(), requestDto.getToken());
        return new ResponseEntity<>(status, HttpStatus.OK);
    }
}
