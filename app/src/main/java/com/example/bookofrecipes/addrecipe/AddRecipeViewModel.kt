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

    private val _recipeId = MutableLiveData<Long>()
    val recipeId: LiveData<Long>
        get() = _recipeId

    private fun initializeRecipeId(title: String) {
        uiScope.launch {
            _recipeId.value = getIdRecipeFromDatabase(title)
        }
    }

    private suspend fun getIdRecipeFromDatabase(title: String): Long {
        return withContext(Dispatchers.IO) {
            dao.getRecipeByTitle(title)?.recipeId
        }!!
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    private suspend fun insertRecipe(recipe: Recipe) {
        withContext(Dispatchers.IO) {
            dao.insertRecipe(recipe)
        }
    }

    private suspend fun insertSteps(steps: List<Step>) {
        withContext(Dispatchers.IO) {
            dao.bulkInsertStep(steps)
        }
    }

    fun onSaveSteps(id: Long) {
        uiScope.launch {
            steps.forEachIndexed { index, step -> step.recipeId = id
                step.numberOfStep = index+1}
            insertSteps(steps)
//            _navigateToRecipes.value = true
        }
    }

    fun onSaveRecipe(title: String) {
        uiScope.launch {
            val recipe = Recipe()
            recipe.title = title

            insertRecipe(recipe)
            initializeRecipeId(title)
        }
    }
}