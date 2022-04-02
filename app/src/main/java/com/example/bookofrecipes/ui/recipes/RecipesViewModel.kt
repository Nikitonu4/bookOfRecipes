package com.example.bookofrecipes.ui.recipes

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.bookofrecipes.data.entity.Recipe
import com.example.bookofrecipes.data.dao.BookOfRecipesDatabaseDao
import com.example.bookofrecipes.data.entity.Ingredient
import com.example.bookofrecipes.data.entity.Step
import kotlinx.coroutines.*

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

    fun openRecipeInfo(recipe: Recipe) {
        uiScope.launch {
            _navigateToRecipe.value = recipe

        }
    }

    fun deleteRecipe(recipe: Recipe){
        uiScope.launch {
            deleteRecipeFromDb(recipe)
            // todo удалить связанные
        }
    }

    private suspend fun deleteRecipeFromDb(recipe: Recipe) {
        withContext(Dispatchers.IO) {
            dao.delete(recipe)
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