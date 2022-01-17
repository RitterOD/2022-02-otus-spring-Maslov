package org.maslov.repository;

import org.maslov.model.Book;

import java.util.List;

public interface BookRepository {
    List<Book> findAll();

    List<Book> findAllByGenreId(Long id);

    List<Book> findAllByAuthorId(Long id);

    Book findById(Long id);

    Book create(Book b);

    int deleteById(Long id);

    int update(Book b);
}
