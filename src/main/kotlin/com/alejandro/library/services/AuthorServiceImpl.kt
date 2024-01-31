package com.alejandro.library.services

import com.alejandro.library.models.*
import com.alejandro.library.payloads.bodyrequest.AuthorRequest
import com.alejandro.library.payloads.dto.AuthorDTO
import com.alejandro.library.payloads.dto.BookDTO
import com.alejandro.library.repositories.AuthorRepository
import com.alejandro.library.repositories.BookRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.web.servlet.resource.NoResourceFoundException

@Service
class AuthorServiceImpl: AuthorService<AuthorRequest, AuthorDTO, Long> {

    // Dependency injection's
    @Autowired
    private lateinit var rep: AuthorRepository

    @Autowired
    private lateinit var bookRep: BookRepository

    override fun getAll(): List<AuthorDTO> {
        return rep.getAllActive().toListAuthorDTO()
    }

    override fun getBooksByAuthorBy(pk: Long): List<BookDTO> {
        val author = rep.findById(pk).orElseThrow {
            throw NoResourceFoundException(HttpMethod.DELETE, "Entity with id $pk not found")
        }

        if (!author.state) throw NoResourceFoundException(HttpMethod.DELETE, "Entity with id $pk not found")

        return bookRep.getByAuthorId(pk).toListBookDTO()
    }

    override fun countAll(): Long {
        return rep.countAll()
    }

    override fun deleteById(pk: Long): AuthorDTO {
        val author = rep.findById(pk).orElseThrow {
            throw NoResourceFoundException(HttpMethod.DELETE, "Entity with id $pk not found")
        }

        if (!author.state) throw NoResourceFoundException(HttpMethod.DELETE, "Entity with id $pk not found")

        rep.disableBook(pk)
        return author.toAuthorDTO()
    }

    override fun save(br: AuthorRequest): AuthorDTO {
        return rep.save(
            Author(
                name = br.name,
                country = br.country,
                books = emptyList()
            )
        ).toAuthorDTO()
    }

    override fun getById(pk: Long): AuthorDTO {
        val author = rep.findById(pk).orElseThrow {
            throw NoResourceFoundException(HttpMethod.GET, "Entity with id $pk not found")
        }

        if(!author.state) throw NoResourceFoundException(HttpMethod.GET, "Entity with id $pk not found")

        return author.toAuthorDTO()
    }
}