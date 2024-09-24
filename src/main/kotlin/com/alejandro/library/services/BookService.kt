package com.alejandro.library.services

interface BookService<in BR, out DTO, in PK> {

    // Basics create and read methods
    fun getAll(): Set<DTO>

    fun getById(pk: PK): DTO

    fun getAllByTerm(term: String): Set<DTO>

    fun save(br: BR): DTO

    fun deleteById(pk: PK): DTO
}