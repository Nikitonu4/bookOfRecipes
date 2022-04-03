package com.example.bookofrecipes.ui.editrecipe

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bookofrecipes.data.dao.BookOfRecipesDatabaseDao

class EditRecipeViewModelFactory(
    private val dao: BookOfRecipesDatabaseDao,
    private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EditRecipeViewModel::class.java)) {
            return EditRecipeViewModel(dao, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

