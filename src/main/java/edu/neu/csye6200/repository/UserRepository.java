package edu.neu.csye6200.repository;

import edu.neu.csye6200.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByName(String name);
    User findByEmail(String email);
}
