package org.maslov.repository;

import org.maslov.model.Author;
import org.maslov.model.Book;
import org.maslov.model.Genre;
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

@Repository
public class GenreRepositoryImpl implements GenreRepository{
    private final NamedParameterJdbcOperations jdbcOperations;

    public GenreRepositoryImpl(NamedParameterJdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }


    public List<Genre> findAll(){
        List<Genre> rv = jdbcOperations.query("SELECT * FROM genres",  new GenreRawMapper());
        return rv;
    }


    public Genre create(Genre genre) {
            Map<String, Object> map = Map.of(
                    "name", genre.getName());
            GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcOperations.update("insert into genres (`name`) values (:name)",
                    new MapSqlParameterSource(map), keyHolder);
            genre.setId((Long)keyHolder.getKey());
            return genre;
    }

    public int update(Genre genre) {
        Map<String, Object> map = Map.of( "id", genre.getId(),
                "name", genre.getName());
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        return jdbcOperations.update("update genres set name = :name where id = :id",
                new MapSqlParameterSource(map), keyHolder);
    }



    public int deleteById(Long id) {
            Map<String, Object> map = Map.of(
                    "id", id);
            GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
            return jdbcOperations.update("delete from genres where id = :id",
                    new MapSqlParameterSource(map), keyHolder);
    }

    public List<Genre> findById(Long id) {
        Map<String, Object> map = Map.of(
                "id", id);
        List<Genre> rv = jdbcOperations.query("SELECT * FROM genres JOIN books ON books.genre_id = genres.id " +
                        "where id = :id",
                new MapSqlParameterSource(map),
                new GenreResultSetExtractor());
        return rv;
    }


    public List<Genre> findByName(String name) {
        Map<String, Object> map = Map.of(
                "name", name);
        List<Genre> rv = jdbcOperations.query("SELECT * FROM genres where name = :name",
                new MapSqlParameterSource(map),
                new GenreRawMapper());
        return rv;
    }


    private static class GenreRawMapper implements RowMapper<Genre> {

        @Override
        public Genre mapRow(ResultSet rs, int rowNum) throws SQLException {
            Genre rv = Genre.builder()
                    .id(rs.getLong("id"))
                    .name(rs.getString("name"))
                    .build();
            return rv;
        }
    }


    private static class GenreResultSetExtractor implements ResultSetExtractor<List<Genre>> {

        @Override
        public List<Genre> extractData(ResultSet rs) throws SQLException, DataAccessException {
            if (!rs.isBeforeFirst()) {
                return List.of();
            } else {
                Genre.GenreBuilder rv = Genre.builder();
                List<Book> lstBook = new ArrayList<>();
                rs.next();
                rv.name(rs.getString("genres.name"));
                rv.id(rs.getLong("genres.id"));
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
