package com.alejandro.library.payloads.dto

data class AuthorDTO(
    val name: String,
    val country: String,
    val books: Set<BookDTO>
)