package com.example.bookofrecipes.ui.steps

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bookofrecipes.data.dao.BookOfRecipesDatabaseDao

class StepsViewModelFactory(
    private val recipeKey: Long,
    private val dao: BookOfRecipesDatabaseDao,
    private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StepsViewModel::class.java)) {
            return StepsViewModel(recipeKey,dao, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

