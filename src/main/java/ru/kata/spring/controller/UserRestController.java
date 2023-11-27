package ru.kata.spring.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import ru.kata.spring.models.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/user")
public class UserRestController {

    @GetMapping
    public ResponseEntity<User> getAUthUser(@AuthenticationPrincipal User user) {
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }
}
