package com.Amir.Competition.dtos;

import com.Amir.Competition.entity.user;
import lombok.Getter;

@Getter
public class UserLoginDto {

    private Long id;
    private user User;
    private String jwt;

    public UserLoginDto() {
        super();
    }

    public UserLoginDto(Long id, user User, String jwt) {
        this.id = id;
        this.User = User;
        this.jwt = jwt;
    }

    public void setId(Long id) {
        this.id = id;
    }
}