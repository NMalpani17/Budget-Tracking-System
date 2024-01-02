package edu.neu.csye6200;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 
 * @author Ruchika Sharma
 * 
 */
@SpringBootApplication
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "edu.neu.csye6200.*")
@EntityScan("edu.neu.csye6200.*")
@ComponentScan(basePackages = {"edu.neu.csye6200.*"})
@ComponentScan("edu.neu.csye6200.annotation")
public class Driver {
	public static void main(String[] args) {
		SpringApplication.run(Driver.class, args);
	}
}
