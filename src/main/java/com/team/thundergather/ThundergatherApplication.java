package com.team.thundergather;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ThundergatherApplication {

    public static void main(String[] args) {
        SpringApplication.run(ThundergatherApplication.class, args);
    }

}
