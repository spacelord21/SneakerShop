package ru.spacelord.sneakershop.sneakershop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.spacelord.sneakershop.sneakershop.dto.UserDTO;
import ru.spacelord.sneakershop.sneakershop.services.UserService;


@RestController
@RequestMapping("/api/v1")
public class AuthorizationController {

    private final UserService userService;

    @Autowired
    public AuthorizationController(UserService userService)  {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login(@AuthenticationPrincipal User user) {
        return user.getAuthorities().toString();
    }

    @PostMapping("/sign")
    public boolean saveUser(@RequestBody UserDTO userDTO) {
        return userService.save(userDTO);
    }

    @PostMapping("/logout")
    public String logout() {
        return "logout";
    }

}
