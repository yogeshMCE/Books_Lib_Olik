package com.example.bookLibrary.Models;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "book")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "bookTitle", nullable = false, unique = true)
    private String title;

    @ManyToOne()
    @JoinColumn(name = "author_id")
    private Author author;

    @Column(name = "isbn", nullable = false,unique = true)
    private String isbn;

    @Column(nullable = false)
    private int publicationYear;

    @Column(columnDefinition = "VARCHAR(5) DEFAULT 'true'")
    private String isAvailable;


}
