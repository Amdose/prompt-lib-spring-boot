package com.amdose.promptlib.database;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan(basePackages = "com.amdose.database")
@EnableJpaRepositories(basePackages = "com.amdose.database")
@ComponentScan(basePackages = "com.amdose.database")
public class DatabaseModule {

    public static void main(String[] args) {
        SpringApplication.run(DatabaseModule.class, args);
    }
}
