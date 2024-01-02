package edu.neu.csye6200.controller;

import edu.neu.csye6200.model.SignUp;
import edu.neu.csye6200.service.SignUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/signup")
@CrossOrigin(origins = "http://localhost:3000")
public class SignUpController {
    @Autowired
    private SignUpService signUpService;

    @PostMapping
    void signUpUser(@RequestBody SignUp signUp) {
        signUpService.register(signUp);
    }

    @PostMapping("/login")
    SignUp getSignUpInfo(@RequestBody SignUp signUp) {
        return signUpService.findSignUpInfo(signUp);
    }
}
