package com.sidof.task;

import com.sidof.task.security.model.RegisterRequest;
import com.sidof.task.security.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@Slf4j
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);


    }

    @Bean
    CommandLineRunner runner(UserService service ){
        return args -> {
            log.info("CREATING DEFAULT USER");
            service.registerAdmin(new RegisterRequest("sidof","sidof@gmail.com","password"));
        };
    }
}
