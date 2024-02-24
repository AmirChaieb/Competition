package com.Amir.Competition.controller;


import com.Amir.Competition.dtos.UserDto;
import com.Amir.Competition.dtos.UserLoginDto;
import com.Amir.Competition.exceptions.AppException;
import com.Amir.Competition.entity.user;
import com.Amir.Competition.service.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/register")
    public user register(@Valid @RequestBody UserDto User) {
        if (User.getEmail() == null || User.getEmail().isEmpty() || User.getPassword() == null || User.getPassword().isEmpty()) {
            throw new AppException("All fields are required.", HttpStatus.BAD_REQUEST);
        }

        return authenticationService.register(User.getEmail(), User.getPassword());
    }

    @PostMapping("/login")
    public UserLoginDto login(@Valid @RequestBody UserDto User) {
        UserLoginDto userLoginDto = authenticationService.login(User.getEmail(), User.getPassword());

        if (userLoginDto.getUser() == null) {
            throw new AppException("Invalid credentials.", HttpStatus.NOT_FOUND);
        }

        return userLoginDto;
    }
}
