package com.alejandro.library.services

interface BookService<in BR, out DTO, in PK> {

    // Basics create and read methods
    fun getAll(): List<DTO>

    fun getById(pk: PK): DTO

    fun getAllByTerm(term: String): List<DTO>

    fun save(br: BR): DTO

    fun deleteById(pk: PK): DTO

    // Count custom queries
    fun countAll(): Long

    fun countByTerm(term: String): Long
}