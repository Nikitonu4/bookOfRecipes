package com.example.bookofrecipes.therecipe

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.bookofrecipes.data.entity.Recipe
import com.example.bookofrecipes.data.dao.BookOfRecipesDatabaseDao
import kotlinx.coroutines.*

class TheRecipeViewModel(
         val recipeId: Long,
    val dao: BookOfRecipesDatabaseDao,
    application: Application
) : AndroidViewModel(application) {
    private var viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    var recipe = Recipe()

    private val _shouldBindView = MutableLiveData<Boolean>()
    val shouldBindView: LiveData<Boolean>
        get() = _shouldBindView

    private suspend fun setRecipeFromDatabase() {
        withContext(Dispatchers.IO) {
             recipe  = dao.getRecipeById(recipeId)!!
        }
    }

    fun initializeRecipe(){
        uiScope.launch {
            setRecipeFromDatabase()
//            getIngredientsDatabase()
            _shouldBindView.value = true
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun bindDone() {
        _shouldBindView.value = false
    }
}