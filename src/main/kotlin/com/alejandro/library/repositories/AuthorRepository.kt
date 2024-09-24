package com.alejandro.library.repositories

import com.alejandro.library.models.Author
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface AuthorRepository : JpaRepository<Author, Long> {
    fun findAllByActiveTrue(): Set<Author>

    fun findByNameAndActiveTrue(name: String): Author

    fun existsByNameAndActiveTrue(name: String): Boolean

    @Modifying
    @Query(value = "UPDATE authors SET state = false WHERE id_author = :id", nativeQuery = true)
    fun disableBook(@Param("id") id: Long)

}