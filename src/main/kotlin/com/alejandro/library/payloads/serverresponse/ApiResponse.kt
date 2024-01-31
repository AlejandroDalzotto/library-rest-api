package com.alejandro.library.payloads.serverresponse

/**
 * @param T the type of elements contained in the collection. The collection is covariant in its element type.
 * About generics [here](https://kotlinlang.org/docs/generics.html).
 */
interface ApiResponse<T> {

    // The next page url.
    val next: String?

    // The previous page url.
    val previous: String?

    // Amount of elements.
    val count: Long

    // The list with the results.
    val result: List<T>?

    // True if all was right, false if something failed.
    val success: Boolean

    val error: Any?
}