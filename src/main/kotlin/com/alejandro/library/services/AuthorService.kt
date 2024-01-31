package com.alejandro.library.services

import com.alejandro.library.payloads.bodyrequest.BookRequest

interface AuthorService<DTO, PK> {

    // Basics create and read methods
    fun getAll(): List<DTO>

    fun getBooksByAuthorBy(pk: PK): List<BookRequest>

    fun getById(pk: PK): DTO

    fun save(dto: DTO): DTO

    fun deleteById(pk: PK): DTO

    // Count custom queries
    fun countAll(): Int
}