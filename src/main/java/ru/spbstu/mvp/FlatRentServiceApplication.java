package ru.spbstu.mvp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class FlatRentServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(FlatRentServiceApplication.class, args);
    }

//    @Bean
//    public CommandLineRunner commandLineRunner(AuthenticationService service) {
//        return args -> {
//            var admin = RegisterRequest.builder()
//                    .firstname("Gleb")
//                    .email("popov.gleb.01@mail.ru")
//                    .password("string")
//                    .role(USER)
//                    .build();
//            System.out.println("Admin token: " + service.register(admin).getAccessToken());
//        };
//    }
}
