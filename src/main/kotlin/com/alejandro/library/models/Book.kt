package com.alejandro.library.models

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
    private val idBook: Long = 0,

    /* "var" it is for mutable values. */
    @Column(name = "name", length = 35, nullable = false, unique = true)
    private var name: String,

    @JsonBackReference(value = "author")
    @JoinColumn(name = "idAuthor")
    @ManyToOne
    private var author: Author,

    @Column(name = "state")
    private var state: Boolean = true
) {

    fun getId(): Long {
        return this.idBook
    }

    fun getName(): String {
        return this.name
    }

    fun getAuthor(): Author {
        return this.author
    }

    fun getState(): Boolean {
        return this.state
    }
}

/**
 * Extension functions for the book to be converted to BookDTO.
 * About extensions [here](https://kotlinlang.org/docs/extensions.html).
 */
fun Book.toBookDTO(): BookDTO {
    return BookDTO(
        id = this.getId(),
        name = this.getName(),
        author = this.getAuthor().getName()
    )
}

fun List<Book>.toListBookDTO(): List<BookDTO> {
    return this.map { it.toBookDTO() }
}
