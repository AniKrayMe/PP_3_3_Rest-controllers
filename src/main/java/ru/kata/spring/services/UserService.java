package ru.kata.spring.services;



import ru.kata.spring.models.User;

import java.util.List;

public interface UserService {

    List<User> listUsers();
    public User save (User user);
    public void update (User user);
    public void delete(Long id);
    public User getById (Long id);
}
