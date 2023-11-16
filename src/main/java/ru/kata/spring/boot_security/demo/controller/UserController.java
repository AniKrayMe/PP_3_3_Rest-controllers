package ru.kata.spring.boot_security.demo.controller;

import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.service.UsersService;
import ru.kata.spring.boot_security.demo.validators.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class UserController {

    private final UserValidator userValidator;
    private final RoleRepository roleRepository;
    private final UsersService usersService;

    @Autowired
    public UserController(UserValidator userValidator, RoleRepository roleRepository, UsersService usersService) {
        this.userValidator = userValidator;
        this.roleRepository = roleRepository;
        this.usersService = usersService;
    }

    @GetMapping()
    public String printAllUsers(ModelMap model) {
        List<User> users = usersService.getAllPersons();
        model.addAttribute("users", users);
        return "allUsers";
    }

    @GetMapping(value = "/{id}")
    public String printUserById(@PathVariable("id") int id, ModelMap model) {
        User user = usersService.getPersonById(id);
        model.addAttribute("user", user);
        return "show";
    }

    @GetMapping(value = "/new")
    public String createUser(ModelMap model) {
        model.addAttribute("user", new User());
        model.addAttribute("allRoles", roleRepository.findAll());
        return "new";
    }

    @PostMapping()
    public String addUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, @RequestParam("selectedRole") String selectedRole) {
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return "new";
        }
        Role role = new Role(selectedRole);
        role.setUser(user);
        user.getRoles().add(role);
        usersService.addNewPerson(user);
        return "redirect:/admin";
    }


    @GetMapping(value = "/{id}/edit")
    public String editUser(ModelMap model, @PathVariable("id") int id) {
        model.addAttribute("user", usersService.getPersonById(id));
        return "edit";
    }

    @PatchMapping(value = "/{id}")
    public String update(@ModelAttribute("user") User user, @PathVariable("id") int id) {
        usersService.changePersonById(id, user);
        return "redirect:/admin";
    }

    @DeleteMapping(value = "/{id}")
    public String deleteUser(@PathVariable("id") int id) {
        usersService.deletePersonById(id);
        return "redirect:/admin";
    }

    @GetMapping(value = "/{id}/change-password")
    public String changePasswordForm(ModelMap model, @PathVariable("id") int id) {
        User user = usersService.getPersonById(id);
        model.addAttribute("user", user);
        return "changePassword";
    }

    @PostMapping(value = "/{id}/change-password")
    public String changePassword(
            @ModelAttribute("user") User user,
            @PathVariable("id") int id,
            @RequestParam("password") String password,
            @RequestParam("confirmPassword") String confirmPassword
    ) {
        if (!password.equals(confirmPassword)) {
            return "redirect:/admin/" + id + "/change-password?error=passwordMismatch";
        }

        usersService.changePassword(id, password);
        return "redirect:/admin";
    }
}
