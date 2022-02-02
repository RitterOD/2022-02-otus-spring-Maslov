package org.maslov.repository;

import org.junit.jupiter.api.Test;
import org.maslov.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.jdbc.Sql;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;


@JdbcTest
@Import({BookRepositoryImpl.class, AuthorRepositoryImpl.class, GenreRepositoryImpl.class})
//@Sql({"classpath:test-schema.sql", "classpath:test-data.sql"})
class BookRepositoryImplTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private GenreRepository genreRepository;

    public static int INITIAL_NUMBER_OF_BOOKS = 5;

    public static final String FIRST_BOOK_NAME = "War and Peace";
    public static final String FIRST_BOOK_AUTHOR_FIRST_NAME = "Leo";
    public static final String FIRST_BOOK_AUTHOR_LAST_NAME = "Tolstoy";
    public static final String FIRST_BOOK_GENRE = "epic novel";
    public static final String FIRST_BOOK_NEW_NAME = "Anna Karenina";

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
        assertEquals(FIRST_BOOK_NAME, b.getName());
        assertEquals(FIRST_BOOK_AUTHOR_FIRST_NAME, b.getAuthor().getFirstName());
        assertEquals(FIRST_BOOK_AUTHOR_LAST_NAME, b.getAuthor().getLastName());
        assertEquals(FIRST_BOOK_GENRE, b.getGenre().getName());
    }

    @Test
    void findByIdNotFound() {
        assertThrows(EmptyResultDataAccessException.class, () -> {
            Book b = bookRepository.findById(1000L);
        });
    }

    @Test
    public void update() {
        Book b = bookRepository.findById(1L);
        b.setName(FIRST_BOOK_NEW_NAME);
        bookRepository.update(b);
        Book tmp = bookRepository.findById(1L);
        assertEquals(FIRST_BOOK_NEW_NAME, tmp.getName());
    }


    @Test
    public void delete() {
        int rv = bookRepository.deleteById(1L);
        assertEquals(1, rv);
        assertThrows(EmptyResultDataAccessException.class, () -> {
            Book b = bookRepository.findById(1L);
        });
    }
}