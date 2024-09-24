package com.alejandro.library.payloads

data class ApiResponse<T>(
    val data: Set<T>,
    val message: String?,
)
