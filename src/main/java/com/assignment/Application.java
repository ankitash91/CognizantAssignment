package com.assignment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


// run with spring boot
@SpringBootApplication
@ComponentScan(basePackages = "com.assignment")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
