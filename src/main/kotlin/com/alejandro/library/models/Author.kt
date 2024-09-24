package com.alejandro.library.models

import com.alejandro.library.payloads.dto.AuthorDTO
import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.*

@Entity
@Table(name = "authors")
data class Author(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(length = 35, nullable = false, unique = true)
    val name: String,

    @Column(length = 20, nullable = false)
    val country: String,

    val active: Boolean = true,

    @JsonManagedReference
    @OneToMany(mappedBy = "author", targetEntity = Book::class, fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    val books: Set<Book>,
)

fun Author.toDto(): AuthorDTO {
    return AuthorDTO(
        this.name,
        this.country,
        this.books.toSetDto()
    )
}

fun Set<Author>.toSetDto(): Set<AuthorDTO> {
    return this.map { AuthorDTO(it.name, it.country, it.books.toSetDto()) }.toSet()
}