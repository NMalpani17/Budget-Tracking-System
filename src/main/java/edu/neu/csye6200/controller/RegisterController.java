package edu.neu.csye6200.controller;

import edu.neu.csye6200.model.User;
import edu.neu.csye6200.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/register")
@CrossOrigin(origins = "http://localhost:3000")
public class RegisterController {

    @Autowired
    private RegisterService registerService;

    @PostMapping
    public User registerUser(@RequestBody User user) {
        return registerService.registerUser(user);
    }
}
