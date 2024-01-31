package com.alejandro.library.controllers

import com.alejandro.library.payloads.bodyrequest.AuthorRequest
import com.alejandro.library.payloads.dto.AuthorDTO
import com.alejandro.library.payloads.serverresponse.AuthorResponse
import com.alejandro.library.payloads.serverresponse.BookResponse
import com.alejandro.library.services.AuthorServiceImpl
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
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
/* Dependency injection through the constructor. */
class AuthorController @Autowired constructor(
    private val service: AuthorServiceImpl
) {

    @GetMapping("/author/all")
    fun getAll(): ResponseEntity<AuthorResponse> {
        val result = service.getAll()
        val count = service.countAll()
        return ResponseEntity.ok().body(
            AuthorResponse(
                count = count,
                next = null,
                previous = null,
                error = null,
                success = true,
                result = result
            )
        )
    }

    @GetMapping("/author/{id}")
    fun getById(@PathVariable id: Long): ResponseEntity<AuthorResponse> {
        val author = service.getById(id)

        return ResponseEntity.ok().body(
            AuthorResponse(
                count = 1,
                next = null,
                previous = null,
                error = null,
                success = true,
                result = listOf(author)
            )
        )
    }

    @GetMapping("/author/{id}/books")
    fun getBooks(@PathVariable(name = "id") id: Long): ResponseEntity<BookResponse> {
        val books = service.getBooksByAuthorBy(id)
        println("id: $id")
        println("books: $books")

        return ResponseEntity.ok().body(
            BookResponse(
                count = 1,
                next = null,
                previous = null,
                error = null,
                success = true,
                result = books
            )
        )
    }

    @PostMapping("/author/create")
    fun save(@RequestBody @Valid bodyRequest: AuthorRequest): ResponseEntity<AuthorResponse> {
        val author = service.save(bodyRequest)

        return ResponseEntity.status(HttpStatus.CREATED).body(
            AuthorResponse(
                count = 1,
                next = null,
                previous = null,
                error = null,
                success = true,
                result = listOf(author)
            )
        )
    }

    @Transactional
    @DeleteMapping("/author/delete/{id}")
    fun delete(@PathVariable(name = "id") id: Long): ResponseEntity<AuthorResponse> {

        val authorDeleted = service.deleteById(id)

        return ResponseEntity.ok().body(
            AuthorResponse(
                next = null,
                previous = null,
                count = 1,
                success = true,
                result = listOf(authorDeleted),
                error = null
            )
        )
    }
}