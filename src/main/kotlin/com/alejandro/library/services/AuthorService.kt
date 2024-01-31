package com.alejandro.library.services

import com.alejandro.library.payloads.dto.BookDTO

interface AuthorService<in BR, out DTO, in PK> {

    // Basics create and read methods
    fun getAll(): List<DTO>

    fun getBooksByAuthorBy(pk: PK): List<BookDTO>

    fun getById(pk: PK): DTO

    fun save(br: BR): DTO

    fun deleteById(pk: PK): DTO

    // Count custom queries
    fun countAll(): Long
}