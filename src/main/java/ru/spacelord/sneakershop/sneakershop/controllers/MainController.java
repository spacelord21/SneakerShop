package ru.spacelord.sneakershop.sneakershop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.spacelord.sneakershop.sneakershop.dto.UserDTO;
import ru.spacelord.sneakershop.sneakershop.services.UserService;

import java.util.List;
import java.util.stream.Collectors;



@RestController
@RequestMapping("/api/v1")
public class MainController {

    private final UserService userService;

    @Autowired
    public MainController(UserService userService)  {
        this.userService = userService;
    }

    @PostMapping("/sign")
    public boolean saveUser(@RequestBody UserDTO userDTO) {
        return userService.save(userDTO);
    }

    @GetMapping("/auth")
    public String hello() {
        return "Hello!";
    }

    @PostMapping("/logout")
    public String logout() {
        System.out.println("разлогинился");
        return "logout";
    }


    @GetMapping("/all-users")
    public List<UserDTO> getAllUsers() {
        return userService.getAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    @GetMapping("/delete={id}")
    public boolean deleteUser(@PathVariable(value = "id") Long id) {
        return userService.delete(id);
    }

    private UserDTO toDto(UserDTO userDTO) {
        return UserDTO.builder()
                .id(userDTO.getId())
                .userName(userDTO.getUserName())
                .email(userDTO.getEmail())
                .role(userDTO.getRole())
                .archive(false)
                .build();
    }
}
