package com.alejandro.library.controllers

import com.alejandro.library.payloads.ApiResponse
import com.alejandro.library.payloads.dto.BookDTO
import com.alejandro.library.payloads.request.BookRequest
import com.alejandro.library.services.BookServiceImpl
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
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
// Dependency injection through the constructor.
class BookController @Autowired constructor(
    private val service: BookServiceImpl
) {

    // classic endpoint to test if the server is up.
    @GetMapping("/ping")
    fun ping(): String {
        return "pong"
    }

    // get all information from books
    @GetMapping("/book/all")
    fun getAll(): ResponseEntity<ApiResponse<BookDTO>> {
        val result = service.getAll()
        val response = ApiResponse(result, null)
        return ResponseEntity.ok().body(response)
    }

    // Filter by term and return the results.
    @GetMapping("/book/filter")
    fun getAllByTerm(@RequestParam(name = "term") term: String): ResponseEntity<ApiResponse<BookDTO>> {
        val result = service.getAllByTerm(term)
        val response = ApiResponse(result, null)
        return ResponseEntity.ok().body(response)
    }

    // Get one book by his id.
    @GetMapping("/book/{id}")
    fun getById(@PathVariable id: Long): ResponseEntity<ApiResponse<BookDTO>> {
        val book = service.getById(id)
        val response = ApiResponse(setOf(book), null)
        return ResponseEntity.ok().body(response)
    }

    // Create a new book in database
    @PostMapping("/book/create")
    fun save(@RequestBody @Valid bodyRequest: BookRequest): ResponseEntity<ApiResponse<BookDTO>> {
        val book = service.save(bodyRequest)
        val response = ApiResponse(setOf(book), "Book saved successfully.")
        return ResponseEntity.status(HttpStatus.CREATED).body(response)
    }

    @Transactional
    @DeleteMapping("/book/delete/{id}")
    fun delete(@PathVariable(name = "id") id: Long): ResponseEntity<ApiResponse<BookDTO>> {

        val bookDeleted = service.deleteById(id)
        val response = ApiResponse(setOf(bookDeleted), "Book removed successfully.")

        return ResponseEntity.ok().body(response)
    }
}