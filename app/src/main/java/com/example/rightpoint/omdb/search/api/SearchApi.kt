package com.example.rightpoint.omdb.search.api

import android.util.Log
import com.example.rightpoint.omdb.OMDBService
import com.example.rightpoint.omdb.shows.Search
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


/**
 * Model Layer Object
 * Object providing model layer operations and hooks for
 * receiving pass/fail information for said ops.
 */
class SearchApi {


    class Query(val query: String, val filter: String = "", var page: Int = 1) {

        fun increment() {
            page++
        }

        fun hashKey() : String {
            return String.format("%s-%d-%s", query, page, filter)
        }
    }


    interface Recipient {

        /**
         * Called if valid response.
         */
        fun onPass(search: Search)

        /**
         * Called if no valid response. Optional message.
         */
        fun onFail(message: String = "")

    }


    val LOG: Boolean = true
    val TAG: String = "SearchApi"
    val API_KEY: String = "c8b6537d"

    var lastQuery: Query? = null
    var lastResult: Search? = null

    var queryCache: HashMap<String,Search> = HashMap()


    fun performSearch(query: String, filter: String, recipient: Recipient) {
        var currentQuery = Query(query, filter)
        if(queryCache.containsKey(currentQuery.hashKey())) {
            queryCache[currentQuery.hashKey()]?.let {
                recipient.onPass(it)
            }
            return
        }
        currentQuery = handelPagination(currentQuery)
        enqueueReqest(currentQuery, filter, recipient)
        lastQuery = currentQuery
    }


    fun handelPagination(currentQuery: Query) : Query {
        if(lastQuery?.query == currentQuery.query && lastResult?.Search?.size == 10) {
            lastQuery?.increment()
            return lastQuery as Query
        } else {
            return currentQuery
        }
    }


    fun enqueueReqest(currentQuery: Query, filter: String, recipient: Recipient) {
        val filmService = OMDBService.create()
        val listShowsRequest = filmService.listShows(API_KEY, currentQuery.query, filter, currentQuery.page)
        listShowsRequest.enqueue(object : Callback<Search.DefaultSearch> {

            override fun onFailure(call: Call<Search.DefaultSearch>, t: Throwable) {
                log(call.toString(), t)
                recipient.onFail()
            }

            override fun onResponse(call: Call<Search.DefaultSearch>, response: Response<Search.DefaultSearch>) {
                handleSearchResponse(currentQuery, response, recipient)
            }
        })
    }


    fun handleSearchResponse(currentQuery: Query, response: Response<Search.DefaultSearch>, recipient: Recipient) {
        if(!response.isSuccessful || response.body() == null || response.body()!!.Error != null) {
            handleError(response, recipient)
            return
        }
        val search = response.body()!!
        queryCache[currentQuery.hashKey()] = search
        recipient.onPass(search)
    }


    fun handleError(response: Response<Search.DefaultSearch>, recipient: Recipient) {
        var responseInfo = response.toString()
        val search = response.body()
        if(search?.Error != null) {
            responseInfo = String.format("%s: %s", responseInfo, search.Error)
        }
        log(responseInfo)
        recipient.onFail()
    }


    fun log(details: String, t: Throwable? = null) {
        if(!LOG) {
            return
        }
        Log.e(TAG, details, t)
    }
}