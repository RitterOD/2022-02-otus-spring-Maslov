package org.maslov.repository;

import org.maslov.model.Author;

import java.util.List;

public interface AuthorRepository {
    List<Author> findAll();

    Author create(Author author);

    int update(Author author);

    List<Author> findById(Long id);

    int deleteById(Long id);

    List<Author> findByFirstNameAndLastName(String firstName, String lastName);
}
