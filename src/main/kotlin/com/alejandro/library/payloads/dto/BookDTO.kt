package com.alejandro.library.payloads.dto

import java.io.Serializable

data class BookDTO(

    private val id: Long,
    private val name: String,
    private val author: String

): Serializable {

    fun getId(): Long {
        return this.id
    }

    fun getName(): String {
        return this.name
    }

    fun getAuthor(): String {
        return this.author
    }

}
