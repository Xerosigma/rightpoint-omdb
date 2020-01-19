package com.example.rightpoint.omdb.shows

import java.io.Serializable

/**
 * A motion picture.
 */
interface Show : Serializable {


    /**
     * The title fo the show.
     */
    val Title: String


    /**
     * The URL to the shows poster.
     */
    val Poster: String


    /**
     * Years film was published
     */
    val Year: String


    /**
     * The imdb unique identifier.
     */
    val imdbID: String


    class DefaultShow constructor(
        override val imdbID: String,
        override val Title: String,
        override val Poster: String,
        override val Year: String): Show {
        /*
        private lateinit var Title: String
        override val title: String
            get() = Title


        private lateinit var Poster: String
        override val posterUrl: String
            get() = Poster

         */

    }

}