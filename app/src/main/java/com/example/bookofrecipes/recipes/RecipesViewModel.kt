package com.example.bookofrecipes.recipes

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.bookofrecipes.data.entity.Recipe
import com.example.bookofrecipes.data.dao.BookOfRecipesDatabaseDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.withContext

class RecipesViewModel(
    val dao: BookOfRecipesDatabaseDao,
    application: Application
) : AndroidViewModel(application) {
    private var viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    val recipes = dao.getAllRecipes()

    private val _navigateToRecipe = MutableLiveData<Recipe>()
    val navigateToRecipe: LiveData<Recipe>
        get() = _navigateToRecipe

    private suspend fun getRecipesFromDatabase(): LiveData<List<Recipe>> {
        return withContext(Dispatchers.IO) {
            var recipes = dao.getAllRecipes()
            recipes
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}