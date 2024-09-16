package com.alejandro.library.models

import com.alejandro.library.payloads.dto.AuthorDTO
import com.alejandro.library.payloads.dto.BookDTO
import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.*

@Entity
@Table(name = "authors")
data class Author(
    // All the properties of the constructor.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val idAuthor: Long = 0,

    @Column(name = "name", length = 35, nullable = false, unique = true)
    var name: String,

    @Column(name = "country", length = 20, nullable = false)
    var country: String,

    @Column(name = "state")
    var state: Boolean = true,

    @Column(name = "books")
    @JsonManagedReference
    @OneToMany(mappedBy = "author", targetEntity = Book::class, fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    var books: List<Book>,
)

fun Author.toAuthorDTO(): AuthorDTO {
    return AuthorDTO(
        id = idAuthor,
        name = name,
        country = country,
        books = books.toListBookDTO()
    )
}

fun List<Author>.toListAuthorDTO(): List<AuthorDTO> {
    return this.map { it.toAuthorDTO() }
}
