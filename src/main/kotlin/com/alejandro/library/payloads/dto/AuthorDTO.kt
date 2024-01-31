package com.alejandro.library.payloads.dto

data class AuthorDTO(

    val id: Long,
    val name: String,
    val country: String,
    val books: List<BookDTO>

)