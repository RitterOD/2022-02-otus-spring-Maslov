package org.maslov.repository;

import org.maslov.model.Author;
import org.maslov.model.Book;
import org.springframework.dao.DataAccessException;
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
        List<Book> rv = jdbcOperations.query("SELECT * FROM books",  new BookRawMapper());
        return rv;
    }

    @Override
    public List<Book> findById(Long id) {
        Map<String, Object> map = Map.of(
                "id", id);
        List<Book> rv = jdbcOperations.query("SELECT * FROM books where id = :id",
                new MapSqlParameterSource(map),
                new BookRawMapper());
        return rv;
    }

    @Override
    public Book insert(Book b) {
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("name", (Object) b.getName());
            map.put("author_id", null);
            map.put("genre_id", null);
            GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcOperations.update("insert into books (`name`, `author_id`, `genre_id`) values (:name, :author_id, :genre_id)",
                    new MapSqlParameterSource(map), keyHolder);
            return null;
        } catch (DataAccessException e) {
            System.out.println("Message" + e.getMessage());
            return null;
        }
    }

    private static class BookRawMapper implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            Book rv = Book.builder()
                    .id(rs.getLong("id"))
                    .name(rs.getString("name"))
                    .genre(null)
                    .author(null)
                    .build();

            return rv;
        }
    }
}
