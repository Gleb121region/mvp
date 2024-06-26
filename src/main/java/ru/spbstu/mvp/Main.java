package ru.spbstu.mvp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableCaching
@EnableScheduling
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

}
