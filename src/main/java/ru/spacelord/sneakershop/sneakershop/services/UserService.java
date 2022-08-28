package ru.spacelord.sneakershop.sneakershop.services;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import ru.spacelord.sneakershop.sneakershop.domain.Role;
import ru.spacelord.sneakershop.sneakershop.dto.UserDTO;

import java.util.Collection;
import java.util.List;

public interface UserService extends UserDetailsService {
    boolean save(UserDTO userDTO);
    List<UserDTO> getAll();
    boolean delete(Long id);


}
