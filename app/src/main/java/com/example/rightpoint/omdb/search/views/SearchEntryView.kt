package com.example.rightpoint.omdb.search.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.rightpoint.omdb.GlideApp
import com.example.rightpoint.omdb.R
import com.example.rightpoint.omdb.shows.Show
import kotlinx.android.synthetic.main.search_entry_view.view.*

/**
 * A search result entry.
 */
open class SearchEntryView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : FrameLayout(context,attrs, defStyleAttr) {

    lateinit var show: Show


    init {
        val layoutInflater = LayoutInflater.from(context)
        layoutInflater.inflate(R.layout.search_entry_view, this, true)
        layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        requestFocus()
    }


    fun bind(show: Show) {
        this.show = show
        makeAccessible()
        showTitle.text = show.Title
        handleImageDownload()
    }

    fun handleImageDownload() {
        GlideApp.with(      this)
            .load(show.Poster)
            .placeholder(R.drawable.ic_launcher_background)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .centerCrop()
            .into(showPoster)
    }


    fun makeAccessible() {
        contentDescription = String.format("%s %s", show.Title, show.Year)
    }

}