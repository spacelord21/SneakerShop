package ru.spacelord.sneakershop.sneakershop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long id;
    private String userName;
    private String password;
    private String matchingPassword;
    private String email;
    private boolean archive;
    private String role;
}
