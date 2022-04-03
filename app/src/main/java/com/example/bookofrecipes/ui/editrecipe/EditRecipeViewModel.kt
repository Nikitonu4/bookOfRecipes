package com.example.bookofrecipes.ui.editrecipe

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.bookofrecipes.data.entity.Recipe
import com.example.bookofrecipes.data.dao.BookOfRecipesDatabaseDao
import com.example.bookofrecipes.data.entity.Ingredient
import com.example.bookofrecipes.data.entity.Step
import kotlinx.coroutines.*

class EditRecipeViewModel(
    val recipeId: Long,
    val dao: BookOfRecipesDatabaseDao,
    application: Application
) : AndroidViewModel(application) {
    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    var recipe = Recipe()
    var ingredients = ArrayList<Ingredient>()
    var steps = ArrayList<Step>()
    var oldIngredientsId = ArrayList<Long>()

    private val _afterInit = MutableLiveData<Boolean>()
    val afterInit: LiveData<Boolean>
        get() = _afterInit

    private val _isExistRecipe = MutableLiveData<Boolean>()
    val isExistRecipe: LiveData<Boolean>
        get() = _isExistRecipe

    private val _canEditRecipe = MutableLiveData<Boolean>()
    val canEditRecipe: LiveData<Boolean>
        get() = _canEditRecipe

    private val _navigateAfterEditRecipe = MutableLiveData<Boolean>()
    val navigateAfterEditRecipe: LiveData<Boolean>
        get() = _navigateAfterEditRecipe

    init {
        initializeRecipe()
    }

    private fun initializeRecipe() {
        uiScope.launch {
            recipe = getRecipeFromDatabase()
            ingredients = getIngredientsDatabase()
            steps = getStepsDatabase()
            ingredients.map { ingredient -> ingredient.ingredientId }.also {
                oldIngredientsId =
                    it as ArrayList<Long>
            }
            _afterInit.value = true
        }
    }

    private suspend fun getRecipeFromDatabase(): Recipe {
        return withContext(Dispatchers.IO) {
            dao.getRecipeById(recipeId)!!
        }
    }

    private suspend fun getIngredientsDatabase(): ArrayList<Ingredient> {
        return withContext(Dispatchers.IO) {
            dao.getIngredientsByRecipeId(recipeId) as ArrayList<Ingredient>
        }
    }

    private suspend fun getStepsDatabase(): ArrayList<Step> {
        return withContext(Dispatchers.IO) {
            dao.getStepsByRecipeId(recipeId) as ArrayList<Step>
        }
    }

    private suspend fun getRecipeByTitleFromDatabase(title: String): Long? {
        return withContext(Dispatchers.IO) {
            dao.getRecipeByTitle(title)?.recipeId
        }
    }

    private suspend fun updateRecipe() {
        withContext(Dispatchers.IO) {
            dao.updateRecipe(recipe)
        }
    }

    private suspend fun updateIngredient(ingredient: Ingredient) {
        withContext(Dispatchers.IO) {
            dao.updateIngredient(ingredient)
        }
    }

    private suspend fun updateStep(step: Step) {
        withContext(Dispatchers.IO) {
            dao.updateStep(step)
        }
    }

    private suspend fun insertSteps(steps: List<Step>) {
        withContext(Dispatchers.IO) {
            dao.bulkInsertSteps(steps)
        }
    }

    private suspend fun insertIngredient(ingredient: Ingredient) {
        withContext(Dispatchers.IO) {
            dao.insertIngredient(ingredient)
        }
    }

    private suspend fun removeSteps() {
        withContext(Dispatchers.IO) {
            dao.removeStepByRecipeId(recipeId)
        }
    }

    private suspend fun deleteIngredients() {
        withContext(Dispatchers.IO) {
            dao.deleteIngredients(oldIngredientsId)
        }
    }

    // проверяем существования рецепта с таким именем
    fun existRecipe(title: String) {
        uiScope.launch {
            if (recipe.title != title) {
                val recipe = getRecipeByTitleFromDatabase(title)
                val isExistRecipe = recipe != null
                _isExistRecipe.value = isExistRecipe
                _canEditRecipe.value = !isExistRecipe
            } else {
                _isExistRecipe.value = false
                _canEditRecipe.value = true
            }
        }
    }

    fun onEditRecipe(title: String) {
        uiScope.launch {
            // если title поменялся
            if (recipe.title != title) {
                recipe.title = title
                updateRecipe()
            }

            updateIngredients()
            updateSteps()

            _navigateAfterEditRecipe.value = true
        }
    }

    private fun updateIngredients() {
        uiScope.launch {
            ingredients.forEach { ingredient ->
                // если ингредиент уже был в старом списке
                if (oldIngredientsId.contains(ingredient.ingredientId)) {
                    oldIngredientsId.remove(ingredient.ingredientId)
                    updateIngredient(ingredient)
                    // если добавили новый(у него ingredientId == 0)
                } else {
                    ingredient.recipeId = recipeId
                    insertIngredient(ingredient)
                }
            }

            // тут останутся ингредиенты, которые мы удалили
            deleteIngredients()
        }
    }

    private fun updateSteps() {
        uiScope.launch {
            removeSteps()
            steps.forEachIndexed { index, step ->
                step.recipeId = recipeId
                step.numberOfStep = index + 1
            }
            insertSteps(steps)
        }
    }

    fun doneNavigating() {
        _navigateAfterEditRecipe.value = false
    }
}