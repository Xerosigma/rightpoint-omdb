package com.example.rightpoint.omdb.search

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.rightpoint.omdb.OMDBService
import com.example.rightpoint.omdb.R
import com.example.rightpoint.omdb.loading.LoadingView
import com.example.rightpoint.omdb.search.api.SearchApi
import com.example.rightpoint.omdb.search.views.SearchFilterView
import com.example.rightpoint.omdb.search.views.SearchView
import com.example.rightpoint.omdb.shows.Search
import com.example.rightpoint.omdb.shows.ShowListActivity


/**
 * Controller Layer Object
 * Lets you search for shows!
 */
class SearchActivity : AppCompatActivity(), SearchView.Controller {

    lateinit var loadingView: LoadingView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.search_activity)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }


    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)

        val searchView = findViewById<SearchView>(R.id.searchView)
        searchView.addController(this)

        loadingView = findViewById<LoadingView>(R.id.loadingView)
        loadingView.visibility = View.GONE
    }


    override fun onSearchResults() {
        loadingView.visibility = View.GONE
    }


    override fun onSearchExecuted(query: String) {
        if(!isConnectedToNetwork()) {
            showUserError(getString(R.string.general_error_network))
            return
        }
        hideKeyboard()
        showFilterView(query)
    }


    fun showFilterView(query: String) {
        val dialogBuilder = AlertDialog.Builder(this)
        val searchFilterView = SearchFilterView(this)
        dialogBuilder.setView(searchFilterView)
        val dialog = dialogBuilder.create()
        searchFilterView.addController(object: SearchFilterView.Controller {
            override fun onFilterSelected(filter: String) {
                dialog.dismiss()
                performSearch(query, filter)
            }
        })
        dialog.show()
    }


    fun performSearch(query: String, filter: String) {
        loadingView.visibility = View.VISIBLE
        OMDBService.searchApi.performSearch(query, filter, object : SearchApi.Recipient {
            override fun onPass(search: Search) {
                showLoading(false)
                manageData(search)
            }

            override fun onFail(message: String) {
                showLoading(false)
                showUserError(getString(R.string.general_error_empty))
            }

        })
    }


    fun manageData(search: Search) {
        val intent = Intent(applicationContext, ShowListActivity::class.java)
        intent.putExtra(ShowListActivity.PARAMETER_SHOWS, search)
        startActivity(intent)
    }


    fun showUserError(message: String) {
        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setMessage(message)
        dialogBuilder.create().show()
    }


    // TODO: Provide custom dialog that follows UX.
    override fun onSearchInvalid(message: String) {
        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setMessage(message)
        dialogBuilder.create().show()
    }


    fun showLoading(show: Boolean) {
        val visibility = if(show) {
            View.VISIBLE
        } else {
            View.GONE
        }
        loadingView.visibility = visibility
    }


    fun Context.isConnectedToNetwork(): Boolean {
        val connectivityManager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        return connectivityManager?.activeNetworkInfo?.isConnectedOrConnecting() ?: false
    }


    fun hideKeyboard() {
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(loadingView.windowToken, 0)
    }
}
