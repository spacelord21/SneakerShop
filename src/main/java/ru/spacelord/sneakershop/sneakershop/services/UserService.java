package ru.spacelord.sneakershop.sneakershop.services;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.spacelord.sneakershop.sneakershop.dto.UserDTO;

import java.util.List;

public interface UserService extends UserDetailsService {
    void save(UserDTO userDTO);
    List<UserDTO> getAll();
    boolean delete(Long id);


}
