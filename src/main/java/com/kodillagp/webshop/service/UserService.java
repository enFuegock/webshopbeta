package com.kodillagp.webshop.service;

import com.kodillagp.webshop.domain.User;
import com.kodillagp.webshop.dto.UserDTO;
import com.kodillagp.webshop.mapper.UserMapper;
import com.kodillagp.webshop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder; // Dodaj ten import
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder; // Dodaj pole dla PasswordEncoder

    @Autowired
    public UserService(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder; // Wstrzykuj PasswordEncoder
    }

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<UserDTO> getUserById(Long id) {
        return userRepository.findById(id).map(userMapper::toDTO);
    }

    public UserDTO createUser(UserDTO userDTO) {
        User user = userMapper.toEntity(userDTO);
        user.setPasswordHash(passwordEncoder.encode(userDTO.getPassword())); // Haszowanie hasła
        return userMapper.toDTO(userRepository.save(user));
    }

    public Optional<UserDTO> updateUser(Long id, UserDTO userDTO) {
        return userRepository.findById(id).map(existingUser -> {
            User updatedUser = userMapper.toEntity(userDTO);
            updatedUser.setUserId(existingUser.getUserId());
            return userMapper.toDTO(userRepository.save(updatedUser));
        });
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
