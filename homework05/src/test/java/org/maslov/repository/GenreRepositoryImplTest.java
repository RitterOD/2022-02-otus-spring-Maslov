package org.maslov.repository;

import org.junit.jupiter.api.Test;
import org.maslov.model.Author;
import org.maslov.model.Genre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@JdbcTest
@Import(GenreRepositoryImpl.class)
@Sql({"classpath:test-schema.sql", "classpath:test-data.sql"})
class GenreRepositoryImplTest {


    @Autowired
    GenreRepository genreRepository;

    private final int GENRES_AMOUNT = 5;
    private final String NEW_GENRE_NAME = "Fantasy";
    private final String OLD_GENRE_NAME = "drama";


    @Test
    void findAll() {
        assertDoesNotThrow(() -> {
            List<Genre> genres = genreRepository.findAll();
            assertEquals(GENRES_AMOUNT, genres.size());
        });
    }

    @Test
    void create() {
        Genre genre = Genre.builder()
                .name(NEW_GENRE_NAME).build();
        genre = genreRepository.create(genre);
        Genre tmp = genreRepository.findById(genre.getId());
        assertEquals(NEW_GENRE_NAME, tmp.getName());

    }

    @Test
    void update() {
        Genre genre = genreRepository.findById(1L);
        assertEquals(OLD_GENRE_NAME, genre.getName());
        genre.setName(NEW_GENRE_NAME);
        genreRepository.update(genre);
        Genre tmp = genreRepository.findById(1L);
        assertEquals(NEW_GENRE_NAME, tmp.getName());

    }

    @Test
    void findById() {
        Genre genre = genreRepository.findById(1L);
        assertEquals(OLD_GENRE_NAME, genre.getName());
    }


    @Test
    void findByIdNotFound() {
        assertThrows(EmptyResultDataAccessException.class, () -> {
            genreRepository.findById(1000L);
        });
    }

    @Test
    void deleteById() {
        int rv = genreRepository.deleteById(1L);
        assertEquals(1, rv);
        assertThrows(EmptyResultDataAccessException.class, () -> {
            genreRepository.findById(1L);
        });
    }

    @Test
    void findByFirstName() {
        Genre genre = genreRepository.findByName(OLD_GENRE_NAME);
        assertEquals(1, genre.getId());
    }

}