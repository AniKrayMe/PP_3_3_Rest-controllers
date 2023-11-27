package ru.kata.spring.services.impl;


import org.springframework.stereotype.Service;
import ru.kata.spring.models.Role;
import ru.kata.spring.repositories.RoleRepository;
import ru.kata.spring.services.RoleService;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<Role> getRoles() {
        return roleRepository.findAll();
    }

}