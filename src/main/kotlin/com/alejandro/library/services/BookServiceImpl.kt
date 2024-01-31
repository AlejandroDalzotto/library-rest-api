package com.alejandro.library.services

import com.alejandro.library.models.Book
import com.alejandro.library.models.toBookDTO
import com.alejandro.library.models.toListBookDTO
import com.alejandro.library.payloads.bodyrequest.BookRequest
import com.alejandro.library.repositories.AuthorRepository
import com.alejandro.library.repositories.BookRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.web.servlet.resource.NoResourceFoundException

@Service
class BookServiceImpl : BookService<BookRequest, Long> {

    /* Dependency injection's */
    @Autowired
    private lateinit var rep: BookRepository

    @Autowired
    private lateinit var repAuthor: AuthorRepository

    override fun getAll(): List<BookRequest> {
        return rep.getAllActive().toListBookDTO()
    }

    override fun getById(pk: Long): BookRequest {
        val book = rep.findById(pk).orElseThrow {
            throw NoResourceFoundException(HttpMethod.GET, "Entity with id $pk not found")
        }

        if(!book.getState()) throw NoResourceFoundException(HttpMethod.GET, "Entity with id $pk not found")

        return book.toBookDTO()
    }

    override fun getAllByTerm(term: String): List<BookRequest> {
        return rep.getAllByTerm(term.lowercase()).toListBookDTO()
    }

    override fun deleteById(pk: Long): BookRequest {
        val book = rep.findById(pk).orElseThrow {
            throw NoResourceFoundException(HttpMethod.DELETE, "Entity with id $pk not found")
        }

        if (!book.getState()) throw NoResourceFoundException(HttpMethod.DELETE, "Entity with id $pk not found")

        rep.disableBook(pk)
        return book.toBookDTO()
    }

    override fun save(dto: BookRequest): BookRequest {

        val author = repAuthor.getByName(dto.author.name)

        return rep.save(
            Book(
                name = dto.name,
                author = author
            )
        ).toBookDTO()
    }

    override fun countAll(): Int {
        return rep.countAll()
    }

    override fun countByTerm(term: String): Int {
        return rep.countAllByTerm(term.lowercase())
    }

}