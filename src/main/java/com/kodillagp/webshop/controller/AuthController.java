package com.kodillagp.webshop.controller;

import com.kodillagp.webshop.dto.UserDTO;
import com.kodillagp.webshop.domain.User;
import com.kodillagp.webshop.mapper.UserMapper;
import com.kodillagp.webshop.repository.UserRepository;
import com.kodillagp.webshop.service.PasswordService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordService passwordService;

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @GetMapping("/signup")
    public String showSignUpForm(Model model) {
        model.addAttribute("userDTO", new UserDTO());
        return "signup";
    }

    @PostMapping("/signup")
    public String signUpUser(@ModelAttribute UserDTO userDTO) {
        User user = userMapper.toEntity(userDTO);
        user.setPasswordHash(passwordService.hashPassword(userDTO.getPassword())); // Hashowanie hasła
        userRepository.save(user);
        return "redirect:/login";
    }
}
