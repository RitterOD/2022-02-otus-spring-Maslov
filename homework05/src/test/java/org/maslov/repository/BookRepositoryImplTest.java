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

    public static String FIRST_BOOK_NAME = "War and Peace";
    public static String FIRST_BOOK_AUTHOR_FIRST_NAME = "Leo";
    public static String FIRST_BOOK_AUTHOR_LAST_NAME = "Tolstoy";
    public static String FIRST_BOOK_GENRE = "epic novel";

    @Test
    public void findAll() {
        assertDoesNotThrow( () -> {
            List<Book> books = bookRepository.findAll();
            for (Book b : books) {
                System.out.println(b.toString());
            }
            assertEquals(INITIAL_NUMBER_OF_BOOKS, books.size());
        });
    }

    @Test
    public void create() {
        Book b = Book.builder().name("TDD2").build();
        Long id = bookRepository.create(b).getId();
        Book tmp = bookRepository.findById(id);
        assertEquals(b.getName(), tmp.getName());
    }

    @Test
    void findById() {
        Book b = bookRepository.findById(1L);
        assertEquals(b.getName(), FIRST_BOOK_NAME);
        assertEquals(b.getAuthor().getFirstName(), FIRST_BOOK_AUTHOR_FIRST_NAME);
        assertEquals(b.getAuthor().getLastName(), FIRST_BOOK_AUTHOR_LAST_NAME);
        assertEquals(b.getGenre().getName(), FIRST_BOOK_GENRE);
    }
}