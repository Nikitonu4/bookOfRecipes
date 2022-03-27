package com.example.bookofrecipes.addrecipe

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.bookofrecipes.data.entity.Recipe
import com.example.bookofrecipes.data.dao.RecipeDatabaseDao
import com.example.bookofrecipes.data.entity.Step
import kotlinx.coroutines.*

class AddRecipeViewModel(
    val dao: RecipeDatabaseDao,
    application: Application
) : AndroidViewModel(application) {
    private var viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    val steps = ArrayList<Step>()

    fun onSave(title: String) {
        uiScope.launch {
            val recipe = Recipe()
            recipe.title = title

            insert(recipe)
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    private suspend fun insert(recipe: Recipe) {
        withContext(Dispatchers.IO) {
            dao.insertRecipe(recipe)
        }
    }
}