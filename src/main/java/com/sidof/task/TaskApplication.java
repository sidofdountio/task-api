package com.sidof.task;

import com.sidof.task.security.model.RegisterRequest;
import com.sidof.task.security.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TaskApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaskApplication.class, args);


    }

    @Bean
    CommandLineRunner runner(UserService service ){
        return args -> {
            service.registerAdmin(new RegisterRequest("sidof","sidof@gmail.com","password"));
        };
    }
}
