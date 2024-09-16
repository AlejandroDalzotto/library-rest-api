package com.alejandro.library.payloads.serverresponse

import com.alejandro.library.payloads.dto.AuthorDTO

class AuthorResponse(
    override val next: String?,
    override val previous: String?,
    override val count: Long,
    override val result: List<AuthorDTO>?,
    override val success: Boolean,
    override val error: Any?
): ApiResponse<AuthorDTO>