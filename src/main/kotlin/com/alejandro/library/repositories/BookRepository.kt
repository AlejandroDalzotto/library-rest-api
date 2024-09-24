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

    fun findAllByActiveTrue(): Set<Book>

    @Query(value = "SELECT * FROM books b WHERE LOWER(b.name) LIKE %:term% AND b.state = true", nativeQuery = true)
    fun findAllByNameIgnoreCaseContaining(term: String): Set<Book>

    @Modifying
    @Query(value = "UPDATE books SET state = false WHERE id_book = :id", nativeQuery = true)
    fun disableBook(@Param("id") id: Long)
}