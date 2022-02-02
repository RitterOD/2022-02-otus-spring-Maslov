package org.maslov;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;


@SpringBootApplication
public class LibraryOrmApp {
    public static void main(String[] args) {
        ApplicationContext context =SpringApplication.run(LibraryOrmApp.class, args);
    }
}