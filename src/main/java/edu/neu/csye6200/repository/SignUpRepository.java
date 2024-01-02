package edu.neu.csye6200.repository;

import edu.neu.csye6200.model.SignUp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SignUpRepository extends JpaRepository<SignUp, Integer> {

    SignUp findByEmail(@Param("email") String email);
}
