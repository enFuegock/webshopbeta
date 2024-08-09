package com.kodillagp.webshop.controller;

import com.kodillagp.webshop.dto.UserDTO;
import com.kodillagp.webshop.domain.User;
import com.kodillagp.webshop.mapper.UserMapper;
import com.kodillagp.webshop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @GetMapping
    public String adminPanel(Model model) {
        List<UserDTO> users = userRepository.findAll().stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList());
        model.addAttribute("users", users);
        return "admin-panel"; // Nazwa pliku szablonu Thymeleaf
    }

    @GetMapping("/user/{id}")
    public String editUser(@PathVariable Long id, Model model) {
        return userRepository.findById(id)
                .map(user -> {
                    UserDTO userDTO = userMapper.toDTO(user);
                    model.addAttribute("user", userDTO);
                    return "edit-user"; // Nazwa pliku szablonu Thymeleaf
                })
                .orElse("redirect:/admin");
    }

    @PostMapping("/user/update")
    public String updateUser(@ModelAttribute UserDTO userDTO) {
        User user = userMapper.toEntity(userDTO);
        userRepository.save(user);
        return "redirect:/admin";
    }
}
