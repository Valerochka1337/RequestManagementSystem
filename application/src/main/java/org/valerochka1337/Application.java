package org.valerochka1337;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("org.valerochka1337.repository")
@EntityScan("org.valerochka1337.entity")
public class Application {
  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }
}
