package com.alejandro.library.models

import com.alejandro.library.payloads.dto.AuthorDTO
import com.alejandro.library.payloads.dto.BookDTO
import jakarta.persistence.*

@Entity
@Table(name = "authors")
data class Author(
    // All the properties of the constructor.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val idAuthor: Long = 0,

    @Column(name = "name", length = 35, nullable = false, unique = true)
    private var name: String,

    @Column(name = "state")
    private var state: Boolean = true,

    @Column(name = "books")
    @OneToMany(mappedBy = "author", targetEntity = Book::class)
    private var books: List<Book>,
) {

    fun getName(): String {
        return this.name
    }

    fun getState(): Boolean {
        return this.state
    }

    fun getBooks(): List<BookDTO> {
        return this.books.toListBookDTO()
    }
}

fun Author.toAuthorDTO(): AuthorDTO {
    return AuthorDTO(this.getName(), this.getBooks())
}

fun List<Author>.toListAuthorDTO(): List<AuthorDTO> {
    return this.map { it.toAuthorDTO() }
}
