package com.example.bookLibrary.Models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Entity
@Table(name = "author")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "authorName", nullable = false)
    private String name;

    @Column(length = 1000, nullable = false)
    private String biography;

    @OneToMany(cascade = CascadeType.ALL, mappedBy ="author" )
    private List<Book> books;

    public Author(String johnDoe, String mail) {
    }
}
