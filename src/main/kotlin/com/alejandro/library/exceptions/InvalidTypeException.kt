package com.alejandro.library.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
data class InvalidTypeException(
    val reason: String?,
    val field: String?,
    val correctType: String
) : RuntimeException("Reason: $reason, in field $field. The correct type is $correctType") {
    companion object {
        private const val serialVersionUID = 1L
    }
}