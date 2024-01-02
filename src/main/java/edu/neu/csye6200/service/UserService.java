package edu.neu.csye6200.service;

import edu.neu.csye6200.model.User;

import java.util.List;

public interface UserService {

    User createUser(User user);

    void deleteUser(int userId);

    User updateUser(int userId, User user);

    User getUserById(int userId);

    User findUserByName(String name);

    User createAdmin(User user);

    List<User> getAllUsers();
}
