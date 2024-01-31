package com.alejandro.library.payloads.bodyrequest

import com.alejandro.library.payloads.dto.AuthorDTO
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

import lombok.Data
import lombok.AllArgsConstructor
import lombok.NoArgsConstructor

@Data
@AllArgsConstructor
@NoArgsConstructor
data class BookRequest(

    @field:NotNull(message = "Name is mandatory")
    @field:NotBlank(message = "Name must be provided")
    @field:Size(min = 3, max = 35, message = "Name must be at least 3 characters and no more than 35.")
    private val name: String,

    @field:NotNull(message = "Author is mandatory")
    private val author: AuthorDTO
)
