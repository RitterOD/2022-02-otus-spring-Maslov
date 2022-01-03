package org.maslov.repository;

import org.maslov.model.Book;

import java.util.List;

public interface BookRepository {
    List<Book> findAll();

    List<Book> findById(Long id);

    Book insert(Book b);
}
