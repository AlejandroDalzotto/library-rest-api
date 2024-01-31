package com.alejandro.library.models

import com.alejandro.library.payloads.dto.AuthorDTO
import com.alejandro.library.payloads.dto.BookDTO
import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Entity
@Table(name = "books")
data class Book(
    /* All the properties of the constructor. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    /* "val" it is for inmutable values. */
    val idBook: Long = 0,

    /* "var" it is for mutable values. */
    @Column(name = "name", length = 50, nullable = false, unique = true)
    var name: String,

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "idAuthor")
    var author: Author,

    @Column(name = "state")
    var state: Boolean = true
)

/**
 * Extension functions for the book to be converted to BookDTO.
 * About extensions [here](https://kotlinlang.org/docs/extensions.html).
 */
fun Book.toBookDTO(): BookDTO {
    return BookDTO(
        id = idBook,
        name = name,
        author = author.name,
        authorCountry = author.country,
    )
}

fun List<Book>.toListBookDTO(): List<BookDTO> {
    return this.map { it.toBookDTO() }
}
