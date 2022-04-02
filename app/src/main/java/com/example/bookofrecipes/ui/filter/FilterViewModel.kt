package com.example.bookofrecipes.ui.filter

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.bookofrecipes.data.entity.Recipe
import com.example.bookofrecipes.data.dao.BookOfRecipesDatabaseDao
import kotlinx.coroutines.*

class FilterViewModel(
    val dao: BookOfRecipesDatabaseDao,
    application: Application
) : AndroidViewModel(application) {

    private var viewModelJob = Job()

    val ingredients = dao.getAllIngredients()
    val chooseIngredients : ArrayList<String> = ArrayList()

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}