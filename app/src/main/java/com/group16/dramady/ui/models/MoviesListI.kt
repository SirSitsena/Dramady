package com.group16.dramady.ui.models

import androidx.lifecycle.LiveData

interface MoviesListI<T> {
    val list: LiveData<List<T>>

    fun update()

}