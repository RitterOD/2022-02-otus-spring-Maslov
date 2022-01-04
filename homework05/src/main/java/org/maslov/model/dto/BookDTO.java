package org.maslov.model.dto;

import lombok.*;
import org.maslov.model.Author;
import org.maslov.model.Genre;

@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {
    private Long id;
    private String name;
    private String genreName;
    private String AuthorFirstName;
    private String AuthorLastName;
}
