package org.maslov.repository;

import org.maslov.model.Genre;

import java.util.List;

public interface GenreRepository {
    List<Genre> findAll();

    Genre create(Genre genre);

    int update(Genre genre);

    int deleteById(Long id);

    Genre findById(Long id);

    Genre findByName(String name);
}
