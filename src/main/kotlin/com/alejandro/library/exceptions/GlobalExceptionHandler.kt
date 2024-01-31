package com.alejandro.library.exceptions

import com.alejandro.library.payloads.serverresponse.BookResponse
import org.springframework.beans.TypeMismatchException
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.validation.ObjectError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import org.springframework.web.servlet.resource.NoResourceFoundException
import java.util.function.Consumer


@ControllerAdvice
class ValidationHandler : ResponseEntityExceptionHandler() {
    override fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException,
        headers: HttpHeaders, status: HttpStatusCode, request: WebRequest
    ): ResponseEntity<Any>? {
        val errors: MutableMap<String, String?> = HashMap()
        ex.bindingResult.allErrors.forEach(Consumer { error: ObjectError ->
            val fieldName = (error as FieldError).field
            val message = error.getDefaultMessage()
            errors[fieldName] = message
        })
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
            BookResponse(
                next = null,
                previous = null,
                count = 0,
                success = false,
                result = null,
                error = errors
            )
        )
    }

    override fun handleTypeMismatch(
        ex: TypeMismatchException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any>? {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
            BookResponse(
                success = false,
                count = 0,
                error = ex.message ?: "Something went wrong, not message provided.",
                next = null,
                previous = null,
                result = null
            )
        )
    }

    override fun handleNoResourceFoundException(
        ex: NoResourceFoundException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any>? {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
            BookResponse(
                success = false,
                count = 0,
                error = ex.message ?: "Something went wrong, not message provided.",
                next = null,
                previous = null,
                result = null
            )
        )
    }

    @ExceptionHandler(InvalidTypeException::class)
    fun invalidTypeHandler(ex: InvalidTypeException): BookResponse {
        return BookResponse(
            success = false,
            count = 0,
            error = ex.message ?: "Something went wrong, not message provided.",
            next = null,
            previous = null,
            result = null
        )
    }
}
