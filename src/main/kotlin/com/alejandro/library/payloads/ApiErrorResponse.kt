package com.alejandro.library.payloads

data class ApiErrorResponse(
    val message: String,
    val errors: Map<String, String?>?
)
