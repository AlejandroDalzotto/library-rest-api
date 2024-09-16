package com.alejandro.library.services

import com.alejandro.library.models.Author
import com.alejandro.library.models.Book
import com.alejandro.library.models.toBookDTO
import com.alejandro.library.models.toListBookDTO
import com.alejandro.library.payloads.bodyrequest.BookRequest
import com.alejandro.library.payloads.dto.BookDTO
import com.alejandro.library.repositories.AuthorRepository
import com.alejandro.library.repositories.BookRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.web.servlet.resource.NoResourceFoundException

@Service
class BookServiceImpl : BookService<BookRequest, BookDTO, Long> {

    /* Dependency injection's */
    @Autowired
    private lateinit var rep: BookRepository

    @Autowired
    private lateinit var repAuthor: AuthorRepository

    override fun getAll(): List<BookDTO> {
        return rep.getAllActive().toListBookDTO()
    }

    override fun getById(pk: Long): BookDTO {
        val book = rep.findById(pk).orElseThrow {
            throw NoResourceFoundException(HttpMethod.GET, "Entity with id $pk not found")
        }

        if (!book.state) throw NoResourceFoundException(HttpMethod.GET, "Entity with id $pk not found")

        return book.toBookDTO()
    }

    override fun getAllByTerm(term: String): List<BookDTO> {
        return rep.getAllByTerm(term.lowercase()).toListBookDTO()
    }

    override fun deleteById(pk: Long): BookDTO {
        val book = rep.findById(pk).orElseThrow {
            throw NoResourceFoundException(HttpMethod.DELETE, "Entity with id $pk not found")
        }

        if (!book.state) throw NoResourceFoundException(HttpMethod.DELETE, "Entity with id $pk not found")

        rep.disableBook(pk)
        return book.toBookDTO()
    }

    override fun save(br: BookRequest): BookDTO {

        val authorExist = repAuthor.existsByName(br.author.name) > 0

        if (!authorExist) {
            repAuthor.save(
                Author(
                    name = br.author.name,
                    country = br.author.country,
                    books = emptyList()
                )
            )
        }

        val author = repAuthor.getByName(br.author.name)

        return rep.save(
            Book(
                name = br.name,
                author = author
            )
        ).toBookDTO()
    }

    override fun countAll(): Long {
        return rep.countAll()
    }

    override fun countByTerm(term: String): Long {
        return rep.countAllByTerm(term.lowercase())
    }

}