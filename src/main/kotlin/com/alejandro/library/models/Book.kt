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
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(length = 50, nullable = false, unique = true)
    val name: String,

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "id")
    val author: Author,

    val active: Boolean = true
)

fun Book.toDto(): BookDTO {
    return BookDTO(this.name, this.author.name, this.author.country)
}

fun Set<Book>.toSetDto(): Set<BookDTO> {
    return this.map { BookDTO(it.name, it.author.name, it.author.country) }.toSet()
}
