package edu.neu.csye6200.model;

import lombok.Data;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Getter
@Table(name = "sign_up")
public class SignUp {

    private static SignUp instance;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name="firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;

    // Private constructor to prevent instantiation outside this class
    private SignUp() {
        // You can initialize any default values or perform other setup here
    }

    // Factory method to get the singleton instance
    public static SignUp getInstance() {
        if (instance == null) {
            synchronized (SignUp.class) {
                if (instance == null) {
                    instance = new SignUp();
                }
            }
        }
        return instance;
    }
}
