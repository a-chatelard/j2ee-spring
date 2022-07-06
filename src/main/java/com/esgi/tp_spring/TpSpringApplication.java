package com.esgi.tp_spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@Configuration
@EntityScan("com.esgi.tp_spring.entities")
@EnableJpaRepositories("com.esgi.tp_spring.repositories")
public class TpSpringApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(TpSpringApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(TpSpringApplication.class);
    }

}
