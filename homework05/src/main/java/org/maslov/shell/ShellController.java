package org.maslov.shell;


//import org.springframework.shell.standard.ShellComponent;

import org.maslov.model.dto.BookDTO;
import org.maslov.repository.AuthorRepository;
import org.maslov.repository.BookRepository;
import org.maslov.repository.GenreRepository;
import org.maslov.service.AppService;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.stereotype.Component;

@ShellComponent
public class ShellController {
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final GenreRepository genreRepository;
    private final AppService appService;

    public ShellController(AuthorRepository authorRepository, BookRepository bookRepository,
                           GenreRepository genreRepository, AppService appService) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.genreRepository = genreRepository;
        this.appService = appService;
    }

    @ShellMethod(key = "lsbook", value = "list of all books")
    String getAllBookList() {
        return appService.getAllBookList();
    }


    @ShellMethod(key = "vbook", value = "view details about particular book. Example vbook --id 1")
    String getBookDetails(@ShellOption long id) {
        return appService.getBookDetails(id);
    }

    @ShellMethod(key = "rmbook", value = "remove book with particular id. Example rmbook --id 1")
    String removeBook(@ShellOption long id) {
        return appService.removeBook(id);
    }

    @ShellMethod(key = "cbook", value = "create book with particular id. Example cbook --name newname")
    String createBook(@ShellOption String name,
                      @ShellOption(defaultValue = ShellOption.NULL) String genre,
                      @ShellOption(defaultValue = ShellOption.NULL) String authorFirstName,
                      @ShellOption(defaultValue = ShellOption.NULL) String authorLastName) {
        BookDTO dto = BookDTO.builder()
                .name(name)
                .genreName(genre)
                .AuthorFirstName(authorFirstName)
                .AuthorLastName(authorLastName)
                .build();
        return appService.createBook(dto);
    }

    @ShellMethod(key = "ubook", value = "update book with particular id. Example ubook --id 1 --name newname")
    String updateBook(@ShellOption long id,
                      @ShellOption String name,
                      @ShellOption(defaultValue = ShellOption.NULL) String genre,
                      @ShellOption(defaultValue = ShellOption.NULL) String authorFirstName,
                      @ShellOption(defaultValue = ShellOption.NULL) String authorLastName
    ) {
        BookDTO dto = BookDTO.builder()
                .id(id)
                .name(name)
                .genreName(genre)
                .AuthorFirstName(authorFirstName)
                .AuthorLastName(authorLastName)
                .build();
        return appService.updateBook(dto);
    }


    @ShellMethod(key = "lsgenre", value = "list of all genres")
    String getAllGenresList() {
        return appService.getAllGenresList();
    }

    @ShellMethod(key = "vgenre", value = "view details about particular genre. Example vgenre --id 1")
    String getGenreDetails(@ShellOption long id) {
        return appService.getGenreDetails(id);
    }


    @ShellMethod(key = "lsauthor", value = "list of all authors")
    String getAllAuthorsList() {
        return appService.getAllAuthors();
    }

    @ShellMethod(key = "vauthor", value = "view details about particular genre. Example vauthor --id 1")
    String getAuthorsDetails(@ShellOption long id) {
        return appService.getAuthorDetails(id);
    }


}
