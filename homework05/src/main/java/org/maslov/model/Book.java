package org.maslov.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Book {
    private Long id;
    private String name;
    private Genre genre;
    private Author author;
}
