package com.alejandro.library.repositories

import com.alejandro.library.models.Book
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

/**
 * using ':' after class or interface name it's for inherit or implementation
 *
 * About inheritance [here](https://kotlinlang.org/docs/inheritance.html).
 *
 * About interfaces [here](https://kotlinlang.org/docs/interfaces.html).
 */
@Repository
interface BookRepository : JpaRepository<Book, Long> {

    @Query(value = "SELECT * FROM books b WHERE b.state = true", nativeQuery = true)
    fun getAllActive(): List<Book>

    @Query(value = "SELECT * FROM books b WHERE LOWER(b.name) LIKE %:term% AND b.state = true", nativeQuery = true)
    fun getAllByTerm(@Param("term") term: String): List<Book>

    @Query(value = "SELECT * FROM books b WHERE b.id_book = :id AND b.state = true", nativeQuery = true)
    fun getByAuthorId(@Param("id") id: Long): List<Book>

    @Query(value = "SELECT COUNT(b.id_book) FROM books b WHERE b.state = true", nativeQuery = true)
    fun countAll(): Int

    @Query(value = "SELECT COUNT(b.id_book) FROM books b WHERE LOWER(b.name) LIKE %:term% AND b.state = true", nativeQuery = true)
    fun countAllByTerm(@Param("term") term: String): Int

    @Modifying
    @Query(value = "UPDATE books SET state = false WHERE id_book = :id", nativeQuery = true)
    fun disableBook(@Param("id") id: Long)
}