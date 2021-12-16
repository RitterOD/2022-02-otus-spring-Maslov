package org.maslov.repository;

import org.maslov.model.Book;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class BookRepository {
    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    public BookRepository(NamedParameterJdbcOperations namedParameterJdbcOperations) {
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
    }

    public List<Book> findAll(){
        List<Book> rv = namedParameterJdbcOperations.query("SELECT * FROM BOOKS",  new BookRawMapper());
        return rv;
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
