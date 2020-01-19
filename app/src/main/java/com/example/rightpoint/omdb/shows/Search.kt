package com.example.rightpoint.omdb.shows

import java.io.Serializable

/**
 * A search result.
 */
interface Search : Serializable {


    /**
     * List of shows found in search.
     */
    val Search: List<Show>

    /**
     * Total results in search.
     */
    val totalResults: Number

    /**
     * True if valid response, false otherwise.
     */
    val Response: Boolean

    /**
     * Sometimes Error
     */
    val Error: String?


    class DefaultSearch constructor(
        override val Search: List<Show.DefaultShow>,
        override val totalResults: Number,
        override val Response: Boolean,
        override val Error: String?
    ): Search

}