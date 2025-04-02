package com.amdose.promptlib.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"com.amdose", "io.swagger"})
@EnableJpaRepositories(basePackages = "com.amdose.promptlib.database.repositories")
@EntityScan(basePackages = "com.amdose.promptlib.database.entities")
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

}
