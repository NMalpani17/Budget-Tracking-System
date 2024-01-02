package edu.neu.csye6200.service;


import edu.neu.csye6200.model.SignUp;

public interface SignUpService {

    void register(SignUp signUp);

    SignUp findSignUpInfo(SignUp signUp);
}
