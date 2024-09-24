package com.alejandro.library.payloads.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

data class AuthorRequest(

    @field:NotNull(message = "Name is mandatory.")
    @field:NotBlank(message = "Please provide a Name.")
    @field:Size(min = 3, max = 35, message = "Name must be at least 3 characters and no more than 35.")
    val name: String,

    @field:NotNull(message = "Country is mandatory.")
    @field:NotBlank(message = "Please provide a country.")
    @field:Size(min = 3, max = 20, message = "Country must be at least 3 characters and no more than 20.")
    val country: String,
)
