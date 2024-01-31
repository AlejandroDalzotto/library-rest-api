package com.alejandro.library.repositories

import com.alejandro.library.models.Author
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface AuthorRepository: JpaRepository<Author, Long> {

    @Query(value = "SELECT * FROM authors a WHERE a.state = true", nativeQuery = true)
    fun getAllActive(): List<Author>

    @Query(value = "SELECT * FROM authors a WHERE a.name = :name AND a.state = true", nativeQuery = true)
    fun getByName(@Param("name") name: String): Author

    @Query(value = "SELECT COUNT(a.id_author) FROM authors a WHERE a.state = true", nativeQuery = true)
    fun countAll(): Long

    @Query(value = "SELECT COUNT(a.id_author) FROM authors a WHERE a.name = :name AND a.state = true", nativeQuery = true)
    fun existsByName(@Param("name") name: String): Long

    @Modifying
    @Query(value = "UPDATE authors SET state = false WHERE id_author = :id", nativeQuery = true)
    fun disableBook(@Param("id") id: Long)

}