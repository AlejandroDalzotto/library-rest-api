package com.alejandro.library.services

import com.alejandro.library.models.*
import com.alejandro.library.payloads.request.AuthorRequest
import com.alejandro.library.payloads.dto.AuthorDTO
import com.alejandro.library.repositories.AuthorRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.web.servlet.resource.NoResourceFoundException

@Service
class AuthorServiceImpl @Autowired constructor(
    private val rep: AuthorRepository,
): AuthorService<AuthorRequest, AuthorDTO, Long> {

    override fun getAll(): Set<AuthorDTO> {
        return rep.findAllByActiveTrue().toSetDto()
    }

    override fun deleteById(pk: Long): AuthorDTO {
        val author = rep.findById(pk).orElseThrow {
            throw NoResourceFoundException(HttpMethod.DELETE, "Entity with id $pk not found")
        }

        if (!author.active) throw NoResourceFoundException(HttpMethod.DELETE, "Entity with id $pk not found")

        rep.disableBook(pk)
        return author.toDto()
    }

    override fun save(br: AuthorRequest): AuthorDTO {

        val newEntry = Author(
            name = br.name,
            country = br.country,
            books = emptySet()
        )

        rep.save(newEntry)

        return newEntry.toDto()
    }

    override fun getById(pk: Long): AuthorDTO {
        val author = rep.findById(pk).orElseThrow {
            throw NoResourceFoundException(HttpMethod.GET, "Entity with id $pk not found")
        }

        if(!author.active) throw NoResourceFoundException(HttpMethod.GET, "Entity with id $pk not found")

        return author.toDto()
    }
}