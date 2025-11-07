package org.example;

import org.example.data.smtp.EmailMessage;
import org.example.services.SmtpService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {

        SpringApplication.run(Main.class, args);

//        SmtpService smtpService = new SmtpService();
//
//        EmailMessage email = new EmailMessage(
//                "Тестовий лист",
//                "<h2>Привіт, це тест!</h2><p>Працює Java SMTP 👌</p>",
//                "tymchuksasho724@gmail.com"
//        );
//
//        smtpService.sendEmail(email);
    }
}