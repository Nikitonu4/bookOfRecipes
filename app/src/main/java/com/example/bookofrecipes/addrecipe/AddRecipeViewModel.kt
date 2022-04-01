package com.example.bookofrecipes.addrecipe

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.bookofrecipes.data.entity.Recipe
import com.example.bookofrecipes.data.dao.BookOfRecipesDatabaseDao
import com.example.bookofrecipes.data.entity.Ingredient
import com.example.bookofrecipes.data.entity.Step
import kotlinx.coroutines.*

class AddRecipeViewModel(
    val dao: BookOfRecipesDatabaseDao,
    application: Application
) : AndroidViewModel(application) {
    private var viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    var steps = ArrayList<Step>()
    var ingredients = ArrayList<Ingredient>()

    private val _canCreateNewRecipe = MutableLiveData<Boolean>()
    val canCreateNewRecipe: LiveData<Boolean>
        get() = _canCreateNewRecipe

    private val _isExistRecipe = MutableLiveData<Boolean>()
    val isExistRecipe: LiveData<Boolean>
        get() = _isExistRecipe

    private val _recipeId = MutableLiveData<Long>()
    val recipeId: LiveData<Long>
        get() = _recipeId

    private val _navigateAfterNewRecipe = MutableLiveData<Boolean>()
    val navigateAfterNewRecipe: LiveData<Boolean>
        get() = _navigateAfterNewRecipe

    private fun initializeRecipeId(title: String) {
        uiScope.launch {
            _recipeId.value = getRecipeByTitleFromDatabase(title)
        }
    }

    // ViewModel->DAO PART

    private suspend fun getRecipeByTitleFromDatabase(title: String): Long? {
        return withContext(Dispatchers.IO) {
            dao.getRecipeByTitle(title)?.recipeId
        }
    }

    private suspend fun insertRecipe(recipe: Recipe) {
        withContext(Dispatchers.IO) {
            dao.insertRecipe(recipe)
        }
    }

    private suspend fun insertSteps(steps: List<Step>) {
        withContext(Dispatchers.IO) {
            dao.bulkInsertSteps(steps)
        }
    }

    private suspend fun insertIngredients(ingredients: List<Ingredient>) {
        withContext(Dispatchers.IO) {
            dao.bulkInsertIngredients(ingredients)
        }
    }
    ///////////////////////////


    fun existRecipe(title: String) {
        uiScope.launch {
            val recipe = getRecipeByTitleFromDatabase(title)
            val isExistRecipe = recipe != null
            _isExistRecipe.value = isExistRecipe
            _canCreateNewRecipe.value = !isExistRecipe
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun onSaveSteps(id: Long) {
        uiScope.launch {
            steps.forEachIndexed { index, step -> step.recipeId = id
                step.numberOfStep = index+1
            }
            insertSteps(steps)
        }
    }

    fun onSaveIngredients(id: Long) {
        uiScope.launch {
            ingredients.forEach { ingredient -> ingredient.recipeId = id }
            insertIngredients(ingredients)
            _navigateAfterNewRecipe.value = true
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

    fun doneNavigating() {
        _navigateAfterNewRecipe.value = false
    }
}