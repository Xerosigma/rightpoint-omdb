package com.example.rightpoint.omdb.search.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rightpoint.omdb.R
import com.example.rightpoint.omdb.search.models.Search
import com.example.rightpoint.omdb.shows.Show
import kotlinx.android.synthetic.main.search_results_view.view.*


/**
 * Displays Show Search results.
 */
open class SearchResultsView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : FrameLayout(context,attrs, defStyleAttr) {

    companion object {

        val VERT: Int = 1

        val HORZ: Int = 2

        val GRID: Int = 3

    }

    var currentConfiguration: Int =
        VERT
    lateinit var search: Search

    init {
        super.onAttachedToWindow()
        val layoutInflater = LayoutInflater.from(context)
        layoutInflater.inflate(R.layout.search_results_view, this, true)
        configureShuffle()
    }


    fun configureShuffle() {
        shuffleAction.setOnClickListener {
            when (currentConfiguration) {
                VERT -> {
                    currentConfiguration =
                        GRID
                    configureGrid(search)
                }
                GRID -> {
                    currentConfiguration =
                        HORZ
                    configureHorz(search)
                }
                HORZ -> {
                    currentConfiguration =
                        VERT
                    configureVert(search)
                }
            }
        }
    }


    fun configureVert(search: Search) {
        configureView(search, LinearLayoutManager(context))
    }


    fun configureHorz(search: Search) {
        configureView(search, LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false))
    }


    fun configureGrid(search: Search) {
        configureView(search, GridLayoutManager(context, 4))
    }


    fun configureView(search: Search, layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context)) {
        this.search = search
        resultsListView.layoutManager = layoutManager
        resultsListView.adapter =
            Adapter(context, search)
    }


    class Adapter(val context: Context, val search: Search) : RecyclerView.Adapter<ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(
                SearchEntryView(
                    context
                )
            )
        }


        override fun getItemCount(): Int {
            return search.Search.size
        }


        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = search.Search[position]
            holder.bind(item)
        }

    }


    class ViewHolder(val view: SearchEntryView) : RecyclerView.ViewHolder(view) {

        fun bind (show: Show) {
            view.bind(show)
            view.setOnClickListener {

            }
        }

    }

}