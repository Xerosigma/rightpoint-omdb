package com.example.rightpoint.omdb.search.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.example.rightpoint.omdb.R
import kotlinx.android.synthetic.main.search_filter_view.view.*

/**
 * A search result entry.
 */
open class SearchFilterView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : LinearLayout(context,attrs, defStyleAttr) {

    val controllers: ArrayList<Controller> = ArrayList()


    open interface Controller {

        fun onFilterSelected(filter: String)

    }


    val movie: String = "movie"
    val series: String = "series"
    val anything: String = ""


    init {
        makeAccessible()
        orientation = VERTICAL
        val layoutInflater = LayoutInflater.from(context)
        layoutInflater.inflate(R.layout.search_filter_view, this, true)
        layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        requestFocus()
        configureViews()
    }


    fun makeAccessible() {
        contentDescription = context.getString(R.string.search_filter_announce)
        accessibilityLiveRegion = View.ACCESSIBILITY_LIVE_REGION_ASSERTIVE
    }


    fun configureViews() {
        moviesAction.setOnClickListener { onFilterSelected(movie) }
        seriesAction.setOnClickListener { onFilterSelected(series) }
        anythingAction.setOnClickListener { onFilterSelected(anything) }
    }


    fun onFilterSelected(filter: String) {
        controllers.forEach {
            it.onFilterSelected(filter)
        }
    }


    fun addController(controller: Controller) {
        controllers.add(controller)
    }


    fun removeController(controller: Controller) {
        controllers.remove(controller)
    }

}