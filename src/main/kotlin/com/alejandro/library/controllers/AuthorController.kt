package com.alejandro.library.controllers

import com.alejandro.library.payloads.ApiResponse
import com.alejandro.library.payloads.dto.AuthorDTO
import com.alejandro.library.payloads.request.AuthorRequest
import com.alejandro.library.services.AuthorServiceImpl
import jakarta.transaction.Transactional
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
/* Dependency injection through the constructor. */
class AuthorController @Autowired constructor(
    private val service: AuthorServiceImpl
) {

    @GetMapping("/author/all")
    fun getAll(): ResponseEntity<ApiResponse<AuthorDTO>> {
        val result = service.getAll()
        val response = ApiResponse(result, null)
        return ResponseEntity.ok().body(response)
    }

    @GetMapping("/author/{id}")
    fun getById(@PathVariable id: Long): ResponseEntity<ApiResponse<AuthorDTO>> {
        val author = service.getById(id)
        val response = ApiResponse(setOf(author), null)
        return ResponseEntity.ok().body(response)
    }

    @PostMapping("/author/create")
    fun save(@RequestBody @Valid bodyRequest: AuthorRequest): ResponseEntity<ApiResponse<AuthorDTO>> {
        val author = service.save(bodyRequest)
        val response = ApiResponse(setOf(author), "Author saved successfully.")
        return ResponseEntity.status(HttpStatus.CREATED).body(response)
    }

    @Transactional
    @DeleteMapping("/author/delete/{id}")
    fun delete(@PathVariable(name = "id") id: Long): ResponseEntity<ApiResponse<AuthorDTO>> {

        val authorDeleted = service.deleteById(id)
        val response = ApiResponse(setOf(authorDeleted), "Author removed successfully.")

        return ResponseEntity.ok().body(response)
    }
}