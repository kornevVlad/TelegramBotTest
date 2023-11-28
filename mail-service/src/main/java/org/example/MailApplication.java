package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MailApplication {

    /**
     * Подключение почтовой рассылки пользователю для подтверждения входа в телеграмм
     */
    public static void main(String[] args) {
        SpringApplication.run(MailApplication.class);
    }
}