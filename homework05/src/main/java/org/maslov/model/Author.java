package org.maslov.model;


import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Author {
    private Long id;
    private String firstName;
    private String LastName;
    private List<Book> bookList;
}
