package com.alejandro.library.services

interface BookService<DTO, PK> {

    // Basics create and read methods
    fun getAll(): List<DTO>

    fun getById(pk: PK): DTO

    fun getAllByTerm(term: String): List<DTO>

    fun save(dto: DTO): DTO

    fun deleteById(pk: PK): DTO

    // Count custom queries
    fun countAll(): Int

    fun countByTerm(term: String): Int
}