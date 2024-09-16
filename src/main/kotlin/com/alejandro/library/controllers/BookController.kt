package com.alejandro.library.controllers

import com.alejandro.library.payloads.bodyrequest.BookRequest
import com.alejandro.library.payloads.serverresponse.BookResponse
import com.alejandro.library.services.BookServiceImpl
import jakarta.transaction.Transactional
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
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
@CrossOrigin("*")
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
    fun getAll(): ResponseEntity<BookResponse> {
        val result = service.getAll()
        val count = service.countAll()
        return ResponseEntity.ok().body(
            BookResponse(
                count = count,
                next = null,
                previous = null,
                error = null,
                success = true,
                result = result
            )
        )
    }

    // Filter by term and return the results.
    @GetMapping("/book/filter")
    fun getAllByTerm(@RequestParam(name = "term") term: String): ResponseEntity<BookResponse> {
        val result = service.getAllByTerm(term)
        val count = service.countByTerm(term)
        return ResponseEntity.ok().body(
            BookResponse(
                count = count,
                next = null,
                previous = null,
                error = null,
                success = true,
                result = result
            )
        )
    }

    // Get one book by his id.
    @GetMapping("/book/{id}")
    fun getById(@PathVariable id: Long): ResponseEntity<BookResponse> {
        val book = service.getById(id)

        return ResponseEntity.ok().body(
            BookResponse(
                count = 1,
                next = null,
                previous = null,
                error = null,
                success = true,
                result = listOf(book)
            )
        )
    }

    // Create a new book in database
    @PostMapping("/book/create")
    fun save(@RequestBody @Valid bodyRequest: BookRequest): ResponseEntity<BookResponse> {
        val book = service.save(bodyRequest)

        return ResponseEntity.status(HttpStatus.CREATED).body(
            BookResponse(
                count = 1,
                next = null,
                previous = null,
                error = null,
                success = true,
                result = listOf(book)
            )
        )
    }

    @Transactional
    @DeleteMapping("/book/delete/{id}")
    fun delete(@PathVariable(name = "id") id: Long): ResponseEntity<BookResponse> {

        val bookDeleted = service.deleteById(id)

        return ResponseEntity.ok().body(
            BookResponse(
                next = null,
                previous = null,
                count = 1,
                success = true,
                result = listOf(bookDeleted),
                error = null
            )
        )
    }
}