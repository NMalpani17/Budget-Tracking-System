package edu.neu.csye6200.service.impl;

import edu.neu.csye6200.enums.Role;
import edu.neu.csye6200.exception.CustomException;
import edu.neu.csye6200.model.User;
import edu.neu.csye6200.model.UserRole;
import edu.neu.csye6200.repository.UserRoleRepository;
import edu.neu.csye6200.service.RegisterService;
import edu.neu.csye6200.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegisterServiceImpl implements RegisterService {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Override
    public User registerUser(User user) {

        validateUser(user);

        // create a User with Role (Admin, User)
        User newUser = userService.createUser(user);

        UserRole userRole = new UserRole(newUser.getId(), newUser, Role.ROLE_USER);
        userRoleRepository.save(userRole);
        return null;
    }

    private void validateUser(User user) {
        // validate user name, email and password
        if (user.getName().isEmpty() || user.getEmail().isEmpty() || user.getPassword().isEmpty()) {
            throw new CustomException("User Name, Email or Password cannot be empty");
        }
    }
}
