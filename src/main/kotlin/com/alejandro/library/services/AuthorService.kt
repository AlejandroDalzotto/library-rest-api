package com.alejandro.library.services

interface AuthorService<in BR, out DTO, in PK> {

    fun getAll(): Set<DTO>

    fun getById(pk: PK): DTO

    fun save(br: BR): DTO

    fun deleteById(pk: PK): DTO
}