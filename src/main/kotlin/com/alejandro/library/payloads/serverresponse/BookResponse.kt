package com.alejandro.library.payloads.serverresponse

import com.alejandro.library.payloads.bodyrequest.BookRequest

class BookResponse(
    override val next: String?,
    override val previous: String?,
    override val count: Int,
    override val result: List<BookRequest>?,
    override val success: Boolean,
    override val error: Any?
) : ApiResponse<BookRequest> {
}