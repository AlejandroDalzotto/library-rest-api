package com.alejandro.library.services

import com.alejandro.library.models.Author
import com.alejandro.library.models.Book
import com.alejandro.library.models.toDto
import com.alejandro.library.models.toSetDto
import com.alejandro.library.payloads.request.BookRequest
import com.alejandro.library.payloads.dto.BookDTO
import com.alejandro.library.repositories.AuthorRepository
import com.alejandro.library.repositories.BookRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.web.servlet.resource.NoResourceFoundException

@Service
class BookServiceImpl @Autowired constructor(
    private val rep: BookRepository,
    private val repAuthor: AuthorRepository
): BookService<BookRequest, BookDTO, Long> {

    override fun getAll(): Set<BookDTO> {
        return rep.findAllByActiveTrue().toSetDto()
    }

    override fun getById(pk: Long): BookDTO {
        val book = rep.findById(pk).orElseThrow {
            throw NoResourceFoundException(HttpMethod.GET, "Entity with id $pk not found")
        }

        if (!book.active) throw NoResourceFoundException(HttpMethod.GET, "Entity with id $pk not found")

        return book.toDto()
    }

    override fun getAllByTerm(term: String): Set<BookDTO> {
        return rep.findAllByNameIgnoreCaseContaining(term.lowercase()).toSetDto()
    }

    override fun deleteById(pk: Long): BookDTO {
        val book = rep.findById(pk).orElseThrow {
            throw NoResourceFoundException(HttpMethod.DELETE, "Entity with id $pk not found")
        }

        if (!book.active) throw NoResourceFoundException(HttpMethod.DELETE, "Entity with id $pk not found")

        rep.disableBook(pk)
        return book.toDto()
    }

    override fun save(br: BookRequest): BookDTO {

        val authorExist = repAuthor.existsByNameAndActiveTrue(br.author.name)

        if (!authorExist) {
            repAuthor.save(
                Author(
                    name = br.author.name,
                    country = br.author.country,
                    books = emptySet()
                )
            )
        }

        val author = repAuthor.findByNameAndActiveTrue(br.author.name)

        return rep.save(
            Book(
                name = br.name,
                author = author
            )
        ).toDto()
    }

}