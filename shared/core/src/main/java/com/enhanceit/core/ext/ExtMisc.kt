package com.enhanceit.core.ext

import androidx.appcompat.widget.SearchView

inline fun SearchView.doOnSubmit(crossinline block: (String) -> Unit) {
    setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?) : Boolean{
            query?.let{
                block.invoke(it)
            }
            return true
        }
        override fun onQueryTextChange(newText: String?) =  true
    })
}