package com.example.commercepjt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class CommercePjtApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommercePjtApplication.class, args);
    }

}
