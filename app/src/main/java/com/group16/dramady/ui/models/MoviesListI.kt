package com.group16.dramady.ui.models

import androidx.lifecycle.LiveData
import com.group16.dramady.storage.entity.AllTimeBestMovies

interface MoviesListI<T> {
    val list: LiveData<List<T>>

    fun update()

}