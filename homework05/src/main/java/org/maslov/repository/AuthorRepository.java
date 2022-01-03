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
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class AuthorRepository {
    private final NamedParameterJdbcOperations jdbcOperations;

    public AuthorRepository(NamedParameterJdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    public List<Author> findAll(){
        List<Author> rv = jdbcOperations.query("SELECT * FROM authors",  new AuthorRawMapper());
        return rv;
    }

    public Author create(Author author) {
        try {
            Map<String, Object> map = Map.of(
                    "first_name", author.getFirstName(),
                    "last_name", author.getLastName());
            GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcOperations.update("insert into authors (`name`, `author_id`, `genre_id`) values (:first_name, :last_name)",
                    new MapSqlParameterSource(map), keyHolder);
            author.setId((Long)keyHolder.getKey());
            return author;
        } catch (DataAccessException e) {
            System.out.println("Message" + e.getMessage());
            return null;
        }
    }

    public int deleteById(Long id) {
        try {
            Map<String, Object> map = Map.of(
                    "id", id);
            GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
            return jdbcOperations.update("delete from authors where id = :id",
                    new MapSqlParameterSource(map), keyHolder);

        } catch (DataAccessException e) {
            System.out.println("Message" + e.getMessage());
            return 0;
        }
    }

    public List<Author> findById(Long id) {
        Map<String, Object> map = Map.of(
                "id", id);
        List<Author> rv = jdbcOperations.query("SELECT * FROM authors where id = :id",
                new MapSqlParameterSource(map),
                new AuthorRawMapper());
        return rv;
    }

    public List<Author> findByFirstNameAndLastName(String firstName, String lastName) {
        Map<String, Object> map = Map.of(
                "first_name", firstName,
                "last_name", lastName);
        List<Author> rv = jdbcOperations.query("SELECT * FROM authors where first_name = :first_name and" +
                "last_name = :last_name",
                new MapSqlParameterSource(map),
                new AuthorRawMapper());
        return rv;
    }


    private static class AuthorRawMapper implements RowMapper<Author> {

        @Override
        public Author mapRow(ResultSet rs, int rowNum) throws SQLException {
            Author rv = Author.builder()
                    .id(rs.getLong("id"))
                    .firstName(rs.getString("first_name"))
                    .LastName(rs.getString("last_name"))
                    .build();
            return rv;
        }
    }



}
