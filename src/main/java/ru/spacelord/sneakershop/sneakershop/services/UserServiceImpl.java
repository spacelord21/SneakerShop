package ru.spacelord.sneakershop.sneakershop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.spacelord.sneakershop.sneakershop.dao.BucketRepository;
import ru.spacelord.sneakershop.sneakershop.dao.UserRepository;
import ru.spacelord.sneakershop.sneakershop.domain.Bucket;
import ru.spacelord.sneakershop.sneakershop.domain.Role;
import ru.spacelord.sneakershop.sneakershop.domain.User;
import ru.spacelord.sneakershop.sneakershop.dto.UserDTO;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService, UserDetailsService{

    private final UserRepository userRepository;
    private final BucketRepository bucketRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,BucketRepository bucketRepository) {
        this.userRepository = userRepository;
        this.bucketRepository = bucketRepository;
    }


    @Override
    public void save(UserDTO userDTO) {
        if(!Objects.equals(userDTO.getPassword(),userDTO.getMatchingPassword())) {
            throw new RuntimeException("Password isn't equals!");
        }
        if(userRepository.findFirstByName(userDTO.getUserName())!=null || userRepository.findByEmail(userDTO.getEmail())!=null) {
            throw new RuntimeException("UserName or Email is occupied!");
        }
        User user = User.builder()
                .name(userDTO.getUserName())
                .password(userDTO.getPassword())
                .email(userDTO.getEmail())
                .role(Role.CLIENT)
                .build();
        userRepository.save(user);
        Bucket bucket = Bucket.builder().user(user).build();
        bucketRepository.save(bucket);
        userRepository.addBucketToUser(bucket,userRepository.findFirstByName(user.getName()).getId());
    }

    @Override
    public List<UserDTO> getAll() {
        return userRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public boolean delete(Long id) {
        try {
            userRepository.deleteById(id);
        } catch (UsernameNotFoundException e) {
            return false;
        }
        return true;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findFirstByName(username);
        if(user==null) {
            throw new UsernameNotFoundException("User Not Found with name : " + username);
        }
        return UserDetailsImpl.build(user);
    }

    private UserDTO toDto(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .userName(user.getName())
                .email(user.getEmail())
                .archive(user.isArchive())
                .role(user.getRole().name())
                .build();
    }



}
