package org.maslov.repository;

import org.junit.jupiter.api.Test;
import org.maslov.model.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@JdbcTest
@Import(AuthorRepositoryImpl.class)
@Sql({"classpath:test-schema.sql", "classpath:test-data.sql"})
class AuthorRepositoryImplTest {

    @Autowired
    AuthorRepository authorRepository;

    private final int AUTHOR_AMOUNT = 5;
    private final String NEW_AUTHOR_FIRST_NAME = "ALEXANDER";
    private final String NEW_AUTHOR_LAST_NAME = "PUSHKIN";
    public static final String AUTHOR_1_ID_FIRST_NAME = "Leo";
    public static final String AUTHOR_1_ID_LAST_NAME = "Tolstoy";

    @Test
    void findAll() {
        assertDoesNotThrow(() -> {
            List<Author> authors = authorRepository.findAll();
            assertEquals(AUTHOR_AMOUNT, authors.size());
        });
    }

    @Test
    void create() {
        Author author = Author.builder()
                .firstName(NEW_AUTHOR_FIRST_NAME)
                .lastName(NEW_AUTHOR_LAST_NAME)
                .build();
        author = authorRepository.create(author);
        Author tmp = authorRepository.findById(author.getId());
        assertEquals(NEW_AUTHOR_FIRST_NAME, tmp.getFirstName());
        assertEquals(NEW_AUTHOR_LAST_NAME, tmp.getLastName());
    }

    @Test
    void update() {
        Author author = authorRepository.findById(1L);
        author.setFirstName(NEW_AUTHOR_FIRST_NAME);
        author.setLastName(NEW_AUTHOR_LAST_NAME);
        authorRepository.update(author);
        Author tmp = authorRepository.findById(1L);
        assertEquals(NEW_AUTHOR_FIRST_NAME, tmp.getFirstName());
        assertEquals(NEW_AUTHOR_LAST_NAME, tmp.getLastName());
    }

    @Test
    void findById() {
        Author author = authorRepository.findById(1L);
        assertEquals(AUTHOR_1_ID_FIRST_NAME, author.getFirstName());
        assertEquals(AUTHOR_1_ID_LAST_NAME, author.getLastName());
    }


    @Test
    void findByIdNotFound() {
        assertThrows(EmptyResultDataAccessException.class, () -> {
            Author author = authorRepository.findById(1000L);

        });
    }

    @Test
    void deleteById() {
        int rv = authorRepository.deleteById(1L);
        assertEquals(1, rv);
        assertThrows(EmptyResultDataAccessException.class, () -> {
            Author b = authorRepository.findById(1L);
        });
    }

    @Test
    void findByFirstNameAndLastName() {
        Author author = authorRepository.findByFirstNameAndLastName(AUTHOR_1_ID_FIRST_NAME, AUTHOR_1_ID_LAST_NAME);
        assertEquals(1, author.getId());
    }

    @Test
    void findByFirstNameAndLastNameNotFoundNullFirstNameNullLastName() {
        assertThrows(EmptyResultDataAccessException.class, () -> {
            Author author = authorRepository.findByFirstNameAndLastName(null, null);
        });
    }

    @Test
    void findByFirstNameAndLastNameNotFoundNullFirstName() {
        assertThrows(EmptyResultDataAccessException.class, () -> {
            Author author = authorRepository.findByFirstNameAndLastName(null, AUTHOR_1_ID_LAST_NAME);
        });
    }

    @Test
    void findByFirstNameAndLastNameNotFoundNullLastName() {
        assertThrows(EmptyResultDataAccessException.class, () -> {
            Author author = authorRepository.findByFirstNameAndLastName(AUTHOR_1_ID_FIRST_NAME, null);
        });
    }
}