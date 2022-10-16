package ru.spacelord.sneakershop.sneakershop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.spacelord.sneakershop.sneakershop.config.jwt.JwtUtils;
import ru.spacelord.sneakershop.sneakershop.dao.UserRepository;
import ru.spacelord.sneakershop.sneakershop.domain.Role;
import ru.spacelord.sneakershop.sneakershop.dto.UserDTO;
import ru.spacelord.sneakershop.sneakershop.payload.request.LoginRequest;
import ru.spacelord.sneakershop.sneakershop.payload.request.SignupRequest;
import ru.spacelord.sneakershop.sneakershop.payload.response.JwtResponse;
import ru.spacelord.sneakershop.sneakershop.payload.response.MessageResponse;
import ru.spacelord.sneakershop.sneakershop.services.UserDetailsImpl;
import ru.spacelord.sneakershop.sneakershop.services.UserService;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/v1")
public class AuthorizationController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserRepository userRepository;

    @Autowired
    public AuthorizationController(UserService userService,
                                   AuthenticationManager authenticationManager,
                                   JwtUtils jwtUtils,
                                   UserRepository userRepository) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),loginRequest.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).toList();
        return ResponseEntity.ok(new JwtResponse(jwt,
                "Bearer",
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }

    @PostMapping("/sign")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signupRequest) {
        if(userRepository.existsByName(signupRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Пользователь с таким именем уже существует!"));
        }
        userService.save(UserDTO.builder()
                        .archive(false)
                        .role(Role.CLIENT.name())
                        .userName(signupRequest.getUsername())
                        .email(signupRequest.getEmail())
                        .password(signupRequest.getPassword())
                        .matchingPassword(signupRequest.getMatchingPassword())
                .build());
        return ResponseEntity.ok(new MessageResponse("Пользователь успешно зарегистрирован!"));
    }
}
