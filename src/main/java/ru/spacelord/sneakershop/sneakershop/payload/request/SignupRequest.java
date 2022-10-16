package ru.spacelord.sneakershop.sneakershop.payload.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.spacelord.sneakershop.sneakershop.domain.Role;

import java.util.Set;

@Getter
@Setter
@Builder
public class SignupRequest {

    private String username;
    private String email;
    private Set<Role> roles;
    private String password;
    private String matchingPassword;
}
