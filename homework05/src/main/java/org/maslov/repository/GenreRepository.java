package org.maslov.repository;

import org.maslov.model.Author;
import org.maslov.model.Genre;
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

@Repository
public class GenreRepository {
    private final NamedParameterJdbcOperations jdbcOperations;

    public GenreRepository(NamedParameterJdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }


    public List<Genre> findAll(){
        List<Genre> rv = jdbcOperations.query("SELECT * FROM genres",  new GenreRawMapper());
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

    public Genre create(Genre genre) {
        try {
            Map<String, Object> map = Map.of(
                    "name", genre.getName());
            GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcOperations.update("insert into genres (`name`) values (:name)",
                    new MapSqlParameterSource(map), keyHolder);
            genre.setId((Long)keyHolder.getKey());
            return genre;
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
            return jdbcOperations.update("delete from genres where id = :id",
                    new MapSqlParameterSource(map), keyHolder);

        } catch (DataAccessException e) {
            System.out.println("Message" + e.getMessage());
            return 0;
        }
    }

    public List<Genre> findById(Long id) {
        Map<String, Object> map = Map.of(
                "id", id);
        List<Genre> rv = jdbcOperations.query("SELECT * FROM genres where id = :id",
                new MapSqlParameterSource(map),
                new GenreRawMapper());
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
}
