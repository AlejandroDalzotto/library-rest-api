package com.alejandro.library.payloads.serverresponse

import com.alejandro.library.payloads.dto.BookDTO

class BookResponse(
    override val next: String?,
    override val previous: String?,
    override val count: Long,
    override val result: List<BookDTO>?,
    override val success: Boolean,
    override val error: Any?
) : ApiResponse<BookDTO> {
}