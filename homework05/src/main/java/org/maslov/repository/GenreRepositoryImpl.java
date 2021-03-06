package org.maslov.repository;

import org.maslov.model.Book;
import org.maslov.model.Genre;
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
import java.util.List;
import java.util.Map;

@Repository
public class GenreRepositoryImpl implements GenreRepository{
    private final NamedParameterJdbcOperations jdbcOperations;

    public GenreRepositoryImpl(NamedParameterJdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }


    @Override
    public List<Genre> findAll(){
        List<Genre> rv = jdbcOperations.query("SELECT * FROM genres",  new GenreRawMapper());
        return rv;
    }


    @Override
    public Genre create(Genre genre) {
            Map<String, Object> map = Map.of(
                    "name", genre.getName());
            GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcOperations.update("insert into genres (`name`) values (:name)",
                    new MapSqlParameterSource(map), keyHolder);
            genre.setId((Long)keyHolder.getKey());
            return genre;
    }

    @Override
    public int update(Genre genre) {
        Map<String, Object> map = Map.of( "id", genre.getId(),
                "name", genre.getName());
        return jdbcOperations.update("update genres set name = :name where id = :id",
                new MapSqlParameterSource(map));
    }



    @Override
    public int deleteById(Long id) {
            Map<String, Object> map = Map.of(
                    "id", id);
            return jdbcOperations.update("delete from genres where id = :id",
                    new MapSqlParameterSource(map));
    }

    @Override
    public Genre findById(Long id) {
        Map<String, Object> map = Map.of(
                "id", id);
        List<Genre> rv = jdbcOperations.query("SELECT * FROM genres where genres.id = :id",
                new MapSqlParameterSource(map),
                new GenreRawMapper());
        if (rv.isEmpty()) {
            throw new EmptyResultDataAccessException("Can't find genre with id = " + id, 1);
        } else if (rv.size() != 1){
            throw new IncorrectResultSizeDataAccessException("Programming error. Find several genres with one id", 1,
                    rv.size());
        } else {
            return rv.get(0);
        }
    }


    @Override
    public Genre findByName(String name) {
        Map<String, Object> map = Map.of(
                "name", name);
        List<Genre> rv = jdbcOperations.query("SELECT * FROM genres where name = :name",
                new MapSqlParameterSource(map),
                new GenreRawMapper());
        if (rv.isEmpty()) {
            throw new EmptyResultDataAccessException("Can't find genre with name = " + name, 1);
        } else if (rv.size() != 1){
            throw new IncorrectResultSizeDataAccessException("Programming error. Find several genres with one id", 1,
                    rv.size());
        } else {
            return rv.get(0);
        }
    }


    private static class GenreRawMapper implements RowMapper<Genre> {

        @Override
        public Genre mapRow(ResultSet rs, int rowNum) throws SQLException {
            Genre rv = new Genre(rs.getLong("id"), rs.getString("name"));
            return rv;
        }
    }

}
