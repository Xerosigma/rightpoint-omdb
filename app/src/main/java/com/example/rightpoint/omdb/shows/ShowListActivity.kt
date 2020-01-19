package com.example.rightpoint.omdb.shows

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.rightpoint.omdb.R
import com.example.rightpoint.omdb.search.models.Search
import com.example.rightpoint.omdb.search.views.SearchResultsView


/**
 * Lets you see shows!
 */
class ShowListActivity : AppCompatActivity() {

    companion object {

        val PARAMETER_SHOWS: String = "SHOWS"

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search_list_activity)
    }


    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        val search: Search = intent.getSerializableExtra(PARAMETER_SHOWS) as Search
        val searchResultsView = findViewById<SearchResultsView>(R.id.searchResultsView)
        searchResultsView.configureView(search)
    }
}
