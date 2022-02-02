package org.maslov.service;


import org.maslov.model.Author;
import org.maslov.model.Book;
import org.maslov.model.Genre;
import org.maslov.model.dto.BookDTO;
import org.maslov.repository.AuthorRepository;
import org.maslov.repository.BookRepository;
import org.maslov.repository.GenreRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppService {

    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;
    private final BookRepository bookRepository;

    public AppService(AuthorRepository authorRepository,
                      GenreRepository genreRepository,
                      BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
        this.bookRepository = bookRepository;
    }

    public String getAllBookList() {
        List<Book> books = bookRepository.findAll();
        StringBuilder sb = new StringBuilder("\n");
        if (books.isEmpty()) {
            return "\nThere is no books";
        } else {
            for (Book b : books) {
                sb.append(bookToStringMapper(b, true, true));
                sb.append("\n");
            }
            return sb.toString();
        }
    }

    public String getBookDetails(Long id) {
        try {
            Book b = bookRepository.findById(id);
            return "\n" + bookToStringMapper(b, true, true);
        } catch (DataAccessException e) {
            return "Book with id = " + id + " not found";
        }
    }

    public String getGenreDetails(Long id) {
        try {
            Genre g = genreRepository.findById(id);
            List<Book> books = bookRepository.findAllByGenreId(g.getId());
            return "\n" + genreToStringMapper(g, books);
        } catch (DataAccessException e) {
            return "Book with id = " + id + " not found";
        }
    }

    public String getAuthorDetails(Long id) {
        try {
            Author author = authorRepository.findById(id);
            List<Book> books = bookRepository.findAllByGenreId(author.getId());
            return "\n" + authorToStringMapper(author, books);
        } catch (DataAccessException e) {
            return "Author with id = " + id + " not found";
        }
    }


    public String removeBook(Long id) {
        try {
            int rv = bookRepository.deleteById(id);
            if (rv == 0) {
                return "\n Error during book removing";
            } else {
                return "";
            }
        } catch (DataAccessException e) {
            return "\n Book with id = " + id + " not found";
        }
    }

    public String getAllGenresList() {
        List<Genre> genres = genreRepository.findAll();
        if (genres.isEmpty()) {
            return "\nThere is no genres";
        } else {
            StringBuilder sb = new StringBuilder();
            for (Genre g : genres) {
                List<Book> books = bookRepository.findAllByGenreId(g.getId());
                sb.append(genreToStringMapper(g, books));
                sb.append("\n");
            }
            return sb.toString();
        }
    }

    public String getAllAuthors() {
        List<Author> authors = authorRepository.findAll();
        if (authors.isEmpty()) {
            return "\nThere is no authors";
        } else {
            StringBuilder sb = new StringBuilder();
            for (Author g : authors) {
                List<Book> books = bookRepository.findAllByAuthorId(g.getId());
                sb.append(authorToStringMapper(g, books));
                sb.append("\n");
            }
            return sb.toString();
        }
    }

    public String createBook(BookDTO dto) {
        Genre genre = null;
        Author author = null;
        if (dto.getGenreName() != null) {
            try {
                genre = genreRepository.findByName(dto.getName());
            } catch (DataAccessException e) {
                return "Can't find genre. Please change genres name or create new";
            }
        }
        if ((dto.getAuthorFirstName() == null && dto.getAuthorLastName() != null) ||
                (dto.getAuthorFirstName() != null && dto.getAuthorLastName() == null)) {
            return "Authors first and last name must be set simultaneously";
        } else if (dto.getAuthorFirstName() != null && dto.getAuthorLastName() != null) {
            try {
                author = authorRepository.findByFirstNameAndLastName(dto.getAuthorFirstName(),
                        dto.getAuthorLastName());
            } catch (DataAccessException e) {
                return "Can't find author. Please change author name or create new";
            }
        }
        Book b = Book.builder()
                .name(dto.getName())
                .author(author)
                .genre(genre)
                .build();
        try {
            b = bookRepository.create(b);
            return "Create book with id = " + b.getId();
        } catch (DataAccessException e) {
            return "Error during book creation";
        }
    }

    public String updateBook(BookDTO dto) {
        Genre genre = null;
        Author author = null;
        if (dto.getGenreName() != null) {
            try {
                genre = genreRepository.findByName(dto.getName());
            } catch (DataAccessException e) {
                return "Can't find genre. Please change genres name or create new";
            }
        }
        if ((dto.getAuthorFirstName() == null && dto.getAuthorLastName() != null) ||
                (dto.getAuthorFirstName() != null && dto.getAuthorLastName() == null)) {
            return "Authors first and last name must be set simultaneously";
        } else if (dto.getAuthorFirstName() != null && dto.getAuthorLastName() != null) {
            try {
                author = authorRepository.findByFirstNameAndLastName(dto.getAuthorFirstName(),
                        dto.getAuthorLastName());
            } catch (DataAccessException e) {
                return "Can't find author. Please change author name or create new";
            }
        }
        Book b = Book.builder()
                .id(dto.getId())
                .name(dto.getName())
                .author(author)
                .genre(genre)
                .build();
        try {
            int rv = bookRepository.update(b);
            if (rv == 0) {
                return "Error during update";
            } else {
                return "Book with id = " + b.getId() + " updated";
            }
        } catch (DataAccessException e) {
            return "Error during book updating";
        }
    }

    private String bookToStringMapper(Book b, boolean showAuthor, boolean showGenre) {
        StringBuilder sb = new StringBuilder(b.getId() + " ");
        sb.append(b.getName() + " ");
        if (b.getAuthor() != null && showAuthor) {
            sb.append(b.getAuthor().getFirstName() + " ");
            sb.append(b.getAuthor().getLastName() + " ");
        }
        if (b.getGenre() != null && showGenre) {
            sb.append(b.getGenre().getName() + " ");
        }
        return sb.toString();
    }

    private String genreToStringMapper(Genre g, List<Book> books) {
        StringBuilder sb = new StringBuilder(g.getId() + " ");
        sb.append(g.getName() + " ");
        if (books != null && !books.isEmpty()) {
            sb.append("\nBook of this genre:\n");
            for(Book b : books) {
                sb.append(bookToStringMapper(b, true, false));
                sb.append("\n");
            }
            sb.append("-------------------------------------\n");
        }
        return sb.toString();
    }

    private String authorToStringMapper(Author a, List<Book> books) {
        StringBuilder sb = new StringBuilder(a.getId() + " ");
        sb.append(a.getFirstName() + " ");
        sb.append(a.getLastName());
        if (books != null && !books.isEmpty()) {
            sb.append("\nBook of this author:\n");
            for(Book b : books) {
                sb.append(bookToStringMapper(b, false, true));
                sb.append("\n");
            }
            sb.append("-------------------------------------\n");
        }
        return sb.toString();
    }
}
