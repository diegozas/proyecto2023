package com.example.proyecto2023s1g2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class Proyecto2023s1g2Application extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(Proyecto2023s1g2Application.class);
    }
    public static void main(String[] args) {
        SpringApplication.run(Proyecto2023s1g2Application.class, args);
    }
}

