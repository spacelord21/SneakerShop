package ru.spacelord.sneakershop.sneakershop.payload.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class LoginRequest {
    private String username;
    private String password;
}
