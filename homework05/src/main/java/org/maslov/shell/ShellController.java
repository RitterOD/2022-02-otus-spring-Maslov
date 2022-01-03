package org.maslov.shell;


//import org.springframework.shell.standard.ShellComponent;

import org.maslov.repository.AuthorRepository;
import org.maslov.repository.BookRepository;
import org.maslov.repository.GenreRepository;
import org.springframework.stereotype.Component;

//@ShellComponent
@Component
public class ShellController {
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final GenreRepository genreRepository;

    public ShellController(AuthorRepository authorRepository, BookRepository bookRepository,
                           GenreRepository genreRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.genreRepository = genreRepository;
    }
}
