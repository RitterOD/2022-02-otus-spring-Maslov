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
        BookRepositoryImpl br = context.getBean(BookRepositoryImpl.class);
        List<Book> rv = br.findAll();
        for (Book b : rv) {
            System.out.println(b.getName());
        }
        Book b = Book.builder()
                .name("TDD")
                .author(null)
                .genre(null)
                .build();
        br.insert(b);
        System.out.println("After\n");
        rv = br.findAll();
        for (Book e : rv) {
            System.out.println(e.getName());
        }
    }
}
