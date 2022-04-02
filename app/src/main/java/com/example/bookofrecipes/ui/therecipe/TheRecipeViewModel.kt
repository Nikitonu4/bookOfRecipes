package com.example.bookofrecipes.ui.therecipe

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.bookofrecipes.data.entity.Recipe
import com.example.bookofrecipes.data.dao.BookOfRecipesDatabaseDao
import com.example.bookofrecipes.data.entity.Ingredient
import kotlinx.coroutines.*

class TheRecipeViewModel(
    val recipeId: Long,
    val dao: BookOfRecipesDatabaseDao,
    application: Application
) : AndroidViewModel(application) {
    private var viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    val recipe = MutableLiveData<Recipe?>()
    val ingredients = MutableLiveData<List<Ingredient?>>()

    init {
        initializeRecipe()
    }

    private fun initializeRecipe() {
        uiScope.launch {
            recipe.value = getRecipeFromDatabase()
            ingredients.value = getIngredientsDatabase()
        }
    }

    private suspend fun getRecipeFromDatabase(): Recipe? {
        return withContext(Dispatchers.IO) {
            dao.getRecipeById(recipeId)!!
        }
    }

    private suspend fun getIngredientsDatabase(): ArrayList<Ingredient?>? {
        return withContext(Dispatchers.IO) {
            dao.getIngredientsForRecipe(recipeId) as ArrayList<Ingredient?>
        }
    }

//    private suspend fun setStepsFromDatabase() {
//        withContext(Dispatchers.IO) {
//            steps = dao.getStepsByRecipeId(recipeId)
//        }
//    }


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}