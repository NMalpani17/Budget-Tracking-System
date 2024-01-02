package edu.neu.csye6200.service.impl;

import edu.neu.csye6200.enums.Role;
import edu.neu.csye6200.exception.CustomException;
import edu.neu.csye6200.model.User;
import edu.neu.csye6200.model.UserRole;
import edu.neu.csye6200.repository.UserRepository;
import edu.neu.csye6200.repository.UserRoleRepository;
import edu.neu.csye6200.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;
import java.util.Comparator;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(int userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public User updateUser(int userId, User user) {
        if (userRepository.findById(userId).isEmpty()) {
            throw new NoResultException("User with ID : " + userId + " not found");
        }
        user.setId(userId);
        return userRepository.save(user);
    }

    @Override
    public User getUserById(int userId) {
        return userRepository.findById(userId).orElse(null);
    }

    @Override
    public User findUserByName(String name) {
        User user = userRepository.findByName(name);
        if (user == null) {
            throw new CustomException("User with name : " + name + " not found");
        }
        return user;
    }

    @Override
    public User createAdmin(User user) {
        validateAdminUser(user);

        // create a User with Role (Admin, User)
        User newUser = createUser(user);

        UserRole userRole = new UserRole(newUser.getId(), newUser, Role.ROLE_ADMIN);
        userRoleRepository.save(userRole);
        return null;
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users =  userRepository.findAll();
        users.sort(Comparator.comparing(User::getName));
        return users;
    }

    private void validateAdminUser(User user) {
        // validate user name, email and password
        if (user.getName().isEmpty() || user.getEmail().isEmpty() || user.getPassword().isEmpty()) {
            throw new CustomException("User Name, Email or Password cannot be empty");
        }
    }
}
