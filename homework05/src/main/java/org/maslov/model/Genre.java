package org.maslov.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Genre {
    private Long id;
    private String name;
    private List<Book> bookList;
}
