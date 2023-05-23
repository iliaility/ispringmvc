package com.epam.springmvc;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class SpringApplication {

     public static void main(String[] args) {
          org.springframework.boot.SpringApplication.run(SpringApplication.class, args);
     }
}