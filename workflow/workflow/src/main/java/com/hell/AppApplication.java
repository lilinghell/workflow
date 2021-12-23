package com.hell;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import springfox.documentation.oas.annotations.EnableOpenApi;

@SpringBootApplication
@EnableJpaAuditing
@EnableOpenApi
public class AppApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppApplication.class, args);
    }
}
