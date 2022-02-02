package org.maslov.repository;

import org.maslov.model.Author;
import org.maslov.model.Book;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class AuthorRepositoryImpl implements AuthorRepository{
    private final NamedParameterJdbcOperations jdbcOperations;

    public AuthorRepositoryImpl(NamedParameterJdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    @Override
    public List<Author> findAll(){
        List<Author> rv = jdbcOperations.query("SELECT id, first_name, last_name FROM authors",  new AuthorRawMapper());
        return rv;
    }

    @Override
    public Author create(Author author) {
        Map<String, Object> map = Map.of(
                "first_name", author.getFirstName(),
                "last_name", author.getLastName());
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcOperations.update("insert into authors (`first_name`, `last_name`) values (:first_name, :last_name)",
                new MapSqlParameterSource(map), keyHolder);
        author.setId((Long) keyHolder.getKey());
        return author;
    }

    @Override
    public int update(Author author) {
        Map<String, Object> map = Map.of("id", author.getId(),
                "first_name", author.getFirstName(),
                "last_name", author.getLastName());
        return jdbcOperations.update("update authors set first_name = :first_name, last_name = :last_name where id = :id",
                new MapSqlParameterSource(map));
    }



    @Override
    public Author findById(Long id) {
        Map<String, Object> map = Map.of(
                "id", id);
        List<Author> rv = jdbcOperations.query("SELECT id, first_name, last_name FROM authors where id = :id", new MapSqlParameterSource(map),
                new AuthorRawMapper());
        if (rv.isEmpty()) {
            throw new EmptyResultDataAccessException("Can't find Author with id = " + id, 1);
        } else if (rv.size() != 1){
            throw new IncorrectResultSizeDataAccessException("Programming error. Find several author with 1 id", 1,
                    rv.size());
        } else {
            return rv.get(0);
        }
    }

    @Override
    public int deleteById(Long id) {
        Map<String, Object> map = Map.of(
                "id", id);
        return jdbcOperations.update("delete from authors where id = :id",
                new MapSqlParameterSource(map));
    }

    @Override
    public Author findByFirstNameAndLastName(String firstName, String lastName) {
        Map<String, Object> map = new HashMap<>();
        map.put("first_name", firstName);
        map.put("last_name", lastName);
        List<Author> rv = jdbcOperations.query("SELECT id, first_name, last_name FROM authors where first_name = :first_name AND " +
                "last_name = :last_name",
                new MapSqlParameterSource(map),
                new AuthorRawMapper());
        if (rv.isEmpty()) {
            throw new EmptyResultDataAccessException("Can't find Author with firstName = " + firstName
                    + " and lastName = " + lastName
                    , 1);
        } else if (rv.size() != 1){
            throw new IncorrectResultSizeDataAccessException("Programming error. Find several author with 1 id", 1,
                    rv.size());
        } else {
            return rv.get(0);
        }
    }


    private static class AuthorRawMapper implements RowMapper<Author> {

        @Override
        public Author mapRow(ResultSet rs, int rowNum) throws SQLException {
            Author rv = Author.builder()
                    .id(rs.getLong("id"))
                    .firstName(rs.getString("first_name"))
                    .lastName(rs.getString("last_name"))
                    .build();
            return rv;
        }
    }
}
