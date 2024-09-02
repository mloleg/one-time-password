package ru.mloleg.onetimepassword;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class OneTimePasswordApplication {

    public static void main(String[] args) {
        SpringApplication.run(OneTimePasswordApplication.class, args);
    }
}
