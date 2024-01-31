package com.alejandro.library.payloads.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

data class AuthorDTO(

    @field:NotNull(message = "Name is mandatory")
    @field:NotBlank(message = "Name must be provided")
    @field:Size(min = 3, max = 35, message = "Name must be at least 3 characters and no more than 35.")
    val name: String,

    @field:NotNull(message = "Please provide an empty list of books.")
    val books: List<BookDTO>
)
