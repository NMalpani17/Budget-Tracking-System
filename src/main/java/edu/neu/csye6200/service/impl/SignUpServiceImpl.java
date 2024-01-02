package edu.neu.csye6200.service.impl;

import edu.neu.csye6200.enums.Role;
import edu.neu.csye6200.exception.CustomException;
import edu.neu.csye6200.model.SignUp;
import edu.neu.csye6200.model.User;
import edu.neu.csye6200.model.UserRole;
import edu.neu.csye6200.repository.SignUpRepository;
import edu.neu.csye6200.repository.UserRepository;
import edu.neu.csye6200.repository.UserRoleRepository;
import edu.neu.csye6200.service.SignUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class SignUpServiceImpl implements SignUpService {

    @Autowired
    SignUpRepository signUpRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserRoleRepository userRoleRepository;

    @Override
    public void register(SignUp signUp) {
        // check if the user already exists
        SignUp signUpData = SignUp.getInstance();
        signUpData = signUpRepository.findByEmail(signUp.getEmail());

        if (!Objects.isNull(signUpData)) {
            throw new CustomException("User has already registered, please try logging in");
        }

        // save the user to the sign-up table
        signUpRepository.save(signUp);

        // save the user to the user table
        User user = new User();
        user.setName((signUp.getFirstName() + " " + signUp.getLastName()));
        user.setEmail(signUp.getEmail());
        user.setPassword(signUp.getPassword());
        User newUser = userRepository.save(user);
        UserRole userRole = new UserRole(newUser.getId(), newUser, Role.ROLE_USER);
        userRoleRepository.save(userRole);

    }

    @Override
    public SignUp findSignUpInfo(SignUp signUp) {
        SignUp signUpData = signUpRepository.findByEmail(signUp.getEmail());

        if (!Objects.isNull(signUpData)) {
            // check is password is right
            if (!signUpData.getPassword().equals(signUp.getPassword())) {
                throw new CustomException("Provided password is incorrect, please retry");
            }
        } else {
            throw new CustomException("Provided username is not correct, please retry");
        }
        return signUpData;
    }
}
