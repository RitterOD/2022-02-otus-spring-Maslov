package org.maslov.repository;

import org.maslov.model.Author;
import org.maslov.model.Book;
import org.maslov.model.Genre;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class BookRepositoryImpl implements BookRepository{
    private final NamedParameterJdbcOperations jdbcOperations;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;

    public BookRepositoryImpl(NamedParameterJdbcOperations jdbcOperations, AuthorRepository authorRepository, GenreRepository genreRepository) {
        this.jdbcOperations = jdbcOperations;
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
    }

    @Override
    public List<Book> findAll(){
        List<Book> rv = jdbcOperations.query("SELECT books.id, books.name, books.author_id, books.genre_id, genres.name, " +
                "authors.first_name, authors.last_name " +
                " FROM books LEFT JOIN authors ON books.author_id = authors.id" +
                " LEFT JOIN" +
                " genres ON books.genre_id = genres.id;",  new BookRawMapper());
        return rv;
    }

    @Override
    public List<Book> findAllByGenreId(Long id) {
        Map<String, Object> map = Map.of(
                "genre_id", id);
        List<Book> rv = jdbcOperations.query("SELECT books.id, books.name, books.author_id, books.genre_id, genres.name, " +
                        "authors.first_name, authors.last_name " +
                        "FROM books LEFT JOIN authors ON books.author_id = authors.id  LEFT JOIN " +
                        "genres ON books.genre_id = genres.id WHERE books.genre_id = :genre_id",
                new MapSqlParameterSource(map),
                new BookRawMapper());
        return rv;
    }

    @Override
    public List<Book> findAllByAuthorId(Long id) {
        Map<String, Object> map = Map.of(
                "author_id", id);
        List<Book> rv = jdbcOperations.query("SELECT books.id, books.name, books.author_id, books.genre_id, genres.name, " +
                        "authors.first_name, authors.last_name " +
                        "FROM books LEFT JOIN authors ON books.author_id = authors.id  LEFT JOIN " +
                        "genres ON books.genre_id = genres.id WHERE books.author_id = :author_id",
                new MapSqlParameterSource(map),
                new BookRawMapper());
        return rv;
    }

    @Override
    public Book findById(Long id) {
        Map<String, Object> map = Map.of(
                "id", id);
        Book rv = jdbcOperations.queryForObject("SELECT books.id, books.name, books.author_id, books.genre_id, genres.name, " +
                        "authors.first_name, authors.last_name " +
                        "FROM books LEFT JOIN authors ON books.author_id = authors.id  LEFT JOIN " +
                        "genres ON books.genre_id = genres.id WHERE books.id = :id",
                new MapSqlParameterSource(map),
                new BookRawMapper());
        return rv;
    }

    @Override
    public Book create(Book b) {
            Map<String, Object> map = new HashMap<>();
            map.put("name", b.getName());
            if (b.getAuthor() != null) {
                map.put("author_id", b.getAuthor().getId());
            } else {
                map.put("author_id", null);
            }
            if (b.getGenre() != null) {
                map.put("genre_id", b.getGenre().getId());
            } else {
                map.put("genre_id", null);
            }
            GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
            int rv = jdbcOperations.update("insert into books (`name`, `author_id`, `genre_id`) values (:name, :author_id, :genre_id)",
                    new MapSqlParameterSource(map), keyHolder);
            if (rv == 0) {
                throw new DataAccessResourceFailureException("Can't insert" + b.toString());
            } else {
                b.setId(keyHolder.getKey().longValue());
            }
            return b;

    }

    @Override
    public int deleteById(Long id) {
        Map<String, Object> map = Map.of(
                "id", id);
        return jdbcOperations.update("delete from books where id = :id",
                new MapSqlParameterSource(map));
    }

    @Override
    public int update(Book b) {
        Map<String, Object> map = new HashMap<>();
        map.put("name", b.getName());
        if (b.getAuthor() != null) {
            map.put("author_id", b.getAuthor().getId());
        } else {
            map.put("author_id", null);
        }
        if (b.getGenre() != null) {
            map.put("genre_id", b.getGenre().getId());
        } else {
            map.put("genre_id", null);
        }
        map.put("id", b.getId());
        return jdbcOperations.update("update books set name = :name, author_id = :author_id, genre_id = :genre_id" +
                " where id = :id",
                new MapSqlParameterSource(map));
    }

    private static class BookRawMapper implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            Genre genre = new  Genre(rs.getLong("books.genre_id"),rs.getString("genres.name"));
            Author author = Author.builder()
                    .id(rs.getLong("books.author_id"))
                    .firstName(rs.getString("authors.first_name"))
                    .lastName(rs.getString("authors.last_name"))
                    .build();
            Book rv = Book.builder()
                    .id(rs.getLong("books.id"))
                    .name(rs.getString("books.name"))
                    .genre(genre)
                    .author(author)
                    .build();
            return rv;
        }
    }
}
