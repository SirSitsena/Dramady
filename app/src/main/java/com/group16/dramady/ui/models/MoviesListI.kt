package com.group16.dramady.ui.models

import androidx.lifecycle.LiveData

// For the interaction with movie ViewModels
interface MoviesListI<T> {
    val list: LiveData<List<T>>

    fun update()

}