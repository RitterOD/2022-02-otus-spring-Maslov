package org.maslov.repository;

import org.junit.jupiter.api.Test;
import org.maslov.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;


@JdbcTest
@Import({BookRepositoryImpl.class, AuthorRepositoryImpl.class, GenreRepositoryImpl.class})
@Sql({"classpath:test-schema.sql", "classpath:test-data.sql"})
class BookRepositoryImplTest {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    GenreRepository genreRepository;

    public static int INITIAL_NUMBER_OF_BOOKS = 5;

    @Test
    public void findAll() {
        List<Book> books = bookRepository.findAll();
        for(Book b: books) {
            System.out.println(b.toString());
        }
        assertEquals(INITIAL_NUMBER_OF_BOOKS, books.size());
    }

    @Test
    public void insert() {
        List<Book> books = bookRepository.findAll();
        for(Book b: books) {
            System.out.println(b.toString());
        }
        assertEquals(INITIAL_NUMBER_OF_BOOKS, books.size());
        Book b = Book.builder().name("TDD2").build();
        bookRepository.insert(b);
        books = bookRepository.findAll();
        for(Book e: books) {
            System.out.println(e.toString());
        }
    }
}