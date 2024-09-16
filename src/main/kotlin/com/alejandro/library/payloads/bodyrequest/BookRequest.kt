package com.alejandro.library.payloads.bodyrequest

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

data class BookRequest(

    @field:NotNull(message = "Name is mandatory")
    @field:NotBlank(message = "Name must be provided")
    @field:Size(min = 3, max = 50, message = "Name must be at least 3 characters and no more than 50.")
    val name: String,

    @field:NotNull(message = "Author is mandatory")
    val author: AuthorRequest
)
