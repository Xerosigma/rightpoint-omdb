package com.example.rightpoint.omdb.search.views

import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.example.rightpoint.omdb.R
import kotlinx.android.synthetic.main.search_view.view.*

open class SearchView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : FrameLayout(context,attrs, defStyleAttr) {

    val controllers: ArrayList<Controller> = ArrayList()


    open interface Controller {

        fun onSearchExecuted(query: String)

        fun onSearchResults()

        fun onSearchInvalid(message: String)

    }


    init {
        val layoutInflater = LayoutInflater.from(context)
        layoutInflater.inflate(R.layout.search_view, this, true)
        confgiureSearchAction()
    }


    fun confgiureSearchAction() {
        executeSearchAction.setOnClickListener {
            if(containsValidData()) {
                onSearchExecuted(titleField.text.toString())
            } else {
                onSearchInvalid()
            }
        }
    }


    fun containsValidData(): Boolean {
        return !TextUtils.isEmpty(titleField.text)
    }


    fun addController(controller: Controller) {
        controllers.add(controller)
    }


    fun removeController(controller: Controller) {
        controllers.remove(controller)
    }


    fun onSearchExecuted(query: String) {
        controllers.forEach {
            it.onSearchExecuted(query)
        }
    }


    fun onSearchResults() {
        controllers.forEach {
            it.onSearchResults()
        }
    }


    fun onSearchInvalid() {
        controllers.forEach {
            it.onSearchInvalid(context.getString(R.string.search_invalid))
        }
    }

}