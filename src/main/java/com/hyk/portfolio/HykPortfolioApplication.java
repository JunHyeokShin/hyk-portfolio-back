package com.hyk.portfolio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class HykPortfolioApplication {

  static void main(String[] args) {
    SpringApplication.run(HykPortfolioApplication.class, args);
  }

}
