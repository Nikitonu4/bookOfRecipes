package com.example.bookofrecipes.ui.recipes

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.bookofrecipes.data.entity.Recipe
import com.example.bookofrecipes.data.dao.BookOfRecipesDatabaseDao
import kotlinx.coroutines.*

class RecipesViewModel(
    val dao: BookOfRecipesDatabaseDao,
    application: Application
) : AndroidViewModel(application) {
    private var viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    val recipes = dao.getAllRecipes()
    var filtered: List<Recipe> = emptyList()

    private val _filteredRecipes = MutableLiveData<Boolean>()
    val filteredRecipes: LiveData<Boolean>
        get() = _filteredRecipes

    private val _navigateToRecipe = MutableLiveData<Recipe>()
    val navigateToRecipe: LiveData<Recipe>
        get() = _navigateToRecipe

    fun openRecipeInfo(recipe: Recipe) {
        uiScope.launch {
            _navigateToRecipe.value = recipe
        }
    }

    fun deleteRecipe(recipe: Recipe) {
        uiScope.launch {
            deleteRecipeFromDb(recipe)
        }
    }

    private suspend fun deleteRecipeFromDb(recipe: Recipe) {
        withContext(Dispatchers.IO) {
            dao.deleteRecipe(recipe)
        }
    }

    fun getFilteredRecipes(array: List<String>) {
        uiScope.launch {
            filtered = getFilteredRecipesDatabase(array)
            _filteredRecipes.value = true
        }
    }

    private suspend fun getFilteredRecipesDatabase(array: List<String>): List<Recipe> {
        return withContext(Dispatchers.IO) {
            dao.getRecipeFilterIngredients(array)
        }
    }

    fun doneNavigating() {
        _navigateToRecipe.value = null
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}