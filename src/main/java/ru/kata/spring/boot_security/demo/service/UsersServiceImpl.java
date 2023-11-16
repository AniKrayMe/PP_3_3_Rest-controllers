package ru.kata.spring.boot_security.demo.service;


import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
public class UsersServiceImpl implements UsersService {

    private final UsersRepository usersRepository;

    @Autowired
    public UsersServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Transactional
    @Override
    public void addNewPerson(User user) {
        usersRepository.save(user);
    }

    @Override
    public User getPersonById(int id) { return usersRepository.getById(id); }

    @Override
    public Optional<User> getPersonByName(String name){ return usersRepository.findByFirstName(name); }
    @Override
    public List<User> getAllPersons() { return usersRepository.findAll(); }

    @Transactional
    @Override
    public void changePersonById(int id, User user) { usersRepository.updateUserById(id, user); }

    @Transactional
    @Override
    public void deletePersonById(int id){ usersRepository.deleteById(id); }

    @Override
    public void changePassword(int userId, String newPassword) {
        User user = usersRepository.findById(userId).orElse(null);
        if (user != null) {
            user.setPassword(newPassword);
            usersRepository.save(user);
        }
    }

}
