package org.maslov.repository;

import org.maslov.model.Author;
import org.maslov.model.Book;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class AuthorRepositoryImpl implements AuthorRepository{
    private final NamedParameterJdbcOperations jdbcOperations;

    public AuthorRepositoryImpl(NamedParameterJdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    @Override
    public List<Author> findAll(){
        List<Author> rv = jdbcOperations.query("SELECT * FROM authors",  new AuthorRawMapper());
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
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        return jdbcOperations.update("update authors set first_name = :first_name, last_name = :last_name where id = :id",
                new MapSqlParameterSource(map), keyHolder);
    }



    @Override
    public List<Author> findById(Long id) {
        Map<String, Object> map = Map.of(
                "id", id);
        List<Author> rv = jdbcOperations.query("SELECT * FROM authors JOIN books ON authors.id = books.authors_id where authors.id = :id",
                new MapSqlParameterSource(map),
                new AuthorResultSetExtractor());
        return rv;
    }

    @Override
    public int deleteById(Long id) {
        Map<String, Object> map = Map.of(
                "id", id);
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        return jdbcOperations.update("delete from authors where id = :id",
                new MapSqlParameterSource(map), keyHolder);
    }

    @Override
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
                    .lastName(rs.getString("last_name"))
                    .build();
            return rv;
        }
    }


    private static class AuthorResultSetExtractor implements ResultSetExtractor<List<Author>> {

        @Override
        public List<Author> extractData(ResultSet rs) throws SQLException, DataAccessException {
            if (!rs.isBeforeFirst()) {
                return List.of();
            } else {
                Author.AuthorBuilder rv = Author.builder();
                List<Book> lstBook = new ArrayList<>();
                rs.next();
                rv.firstName(rs.getString("authors.first_name"));
                rv.lastName(rs.getString("authors.last_name"));
                rv.id(rs.getLong("authors.id"));
                lstBook.add(getBook(rs));
                while(rs.next()) {
                    lstBook.add(getBook(rs));
                }
                return List.of(rv.build());
            }
        }

        private Book getBook(ResultSet rs) throws SQLException {
            Book rv = Book.builder()
                    .id(rs.getLong("books.id"))
                    .name(rs.getString("books.name"))
                    .build();

            return rv;
        }
    }



}
