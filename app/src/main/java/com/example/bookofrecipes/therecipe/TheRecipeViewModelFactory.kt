package com.example.bookofrecipes.therecipe

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bookofrecipes.data.dao.BookOfRecipesDatabaseDao

class TheRecipeViewModelFactory(
    private val recipeKey: Long,
    private val dao: BookOfRecipesDatabaseDao,
    private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TheRecipeViewModel::class.java)) {
            return TheRecipeViewModel(recipeKey,dao, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

