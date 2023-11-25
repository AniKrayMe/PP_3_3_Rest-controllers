package ru.kata.spring.boot_security.demo.controller;

import org.springframework.ui.Model;
import ru.kata.spring.boot_security.demo.services.UsersService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import java.security.Principal;

@Controller
public class UserController {

    private UsersService usersService;

    public UserController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping("/user")
    public String getUsers(Model model, Principal principal) {
        model.addAttribute("usingUser", usersService.getUserByUsername(principal.getName()));
        return "user";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }
}
