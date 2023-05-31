package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = {"org.example.dao"})
public class BankCustomersStarter {
    public static void main(String[] args) {
        SpringApplication.run(BankCustomersStarter.class, args);
    }
}
