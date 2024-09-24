package com.alejandro.library.exceptions

import com.alejandro.library.payloads.ApiErrorResponse
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

        val response = ApiErrorResponse(message = "Something went wrong.", errors)
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response)
    }

    override fun handleTypeMismatch(
        ex: TypeMismatchException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any>? {
        val response = ApiErrorResponse(message = "Something went wrong.", errors = null)
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response)
    }

    override fun handleNoResourceFoundException(
        ex: NoResourceFoundException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any>? {
        val response = ApiErrorResponse(message = "Something went wrong.", errors = null)
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response)
    }

    @ExceptionHandler(InvalidTypeException::class)
    fun invalidTypeHandler(ex: InvalidTypeException): ResponseEntity<Any>? {
        val response = ApiErrorResponse(message = "Something went wrong.", errors = null)
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response)
    }
}
