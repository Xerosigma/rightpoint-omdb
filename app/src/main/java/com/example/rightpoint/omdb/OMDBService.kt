package com.example.rightpoint.omdb

import com.example.rightpoint.omdb.search.api.SearchApi
import com.example.rightpoint.omdb.shows.Search
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * The OMDB Service
 * URL Sample: https://www.omdbapi.com/?apikey=c8b6537d&s=batman&type=movie
 * Types: movie, series, episode - Query string  param of "type"
 */
interface OMDBService {

    @GET("/")
    fun listShows(
        @Query("apikey") apiKey: String,
        @Query("s") title: String,
        @Query("type") filter: String,
        @Query("page") page: Int
    ) : Call<Search.DefaultSearch>


    companion object {

        val searchApi = SearchApi()

        fun create(): OMDBService {
            val retrofitBuilder = Retrofit.Builder().baseUrl(getBaseUrl())
            retrofitBuilder.addConverterFactory(GsonConverterFactory.create())
            return retrofitBuilder.build().create(OMDBService::class.java)
        }

        fun getBaseUrl() : String {
            return "https://www.omdbapi.com"
        }
    }
}