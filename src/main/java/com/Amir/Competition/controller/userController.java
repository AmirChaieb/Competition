package com.Amir.Competition.controller;
import com.Amir.Competition.dtos.UserDto;
import com.Amir.Competition.entity.user;
import com.Amir.Competition.service.userService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("api/v1/user")
@RestController
@CrossOrigin("*")



public class userController {
    private final userService UserService;

    @GetMapping("/{userId}")
    public user getUserById(@PathVariable Long userId, Authentication authentication) {
        return UserService.getUserById(userId, authentication);
    }

    @PutMapping("/update/{userId}")
    public user updateUserById(@PathVariable Long userId, @RequestBody UserDto userDto, Authentication authentication) {
        return UserService.updateUserById(userId, userDto, authentication);
    }

}
