package com.example.rightpoint.omdb.loading

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.example.rightpoint.omdb.R

open class LoadingView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : FrameLayout(context,attrs, defStyleAttr) {

    init {
        val layoutInflater = LayoutInflater.from(context)
        layoutInflater.inflate(R.layout.loading_view, this, true)
    }

}