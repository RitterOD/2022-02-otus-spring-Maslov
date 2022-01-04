package org.maslov;


import org.maslov.model.Book;
import org.maslov.repository.BookRepositoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.List;


@SpringBootApplication
public class BookJdbcApp {
    public static void main(String[] args) {
        ApplicationContext context =SpringApplication.run(BookJdbcApp.class, args);
    }
}
