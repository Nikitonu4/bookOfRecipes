package com.example.bookofrecipes.ui.steps


import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.bookofrecipes.data.dao.BookOfRecipesDatabaseDao
import com.example.bookofrecipes.data.entity.Ingredient
import com.example.bookofrecipes.data.entity.Recipe
import com.example.bookofrecipes.data.entity.Step
import kotlinx.coroutines.*

class StepsViewModel(
    val recipeId: Long,
    val dao: BookOfRecipesDatabaseDao,
    application: Application
) : AndroidViewModel(application) {
    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    val steps = MutableLiveData<List<Step?>>()

    private val _currentStep = MutableLiveData<Step>()
    val currentStep: LiveData<Step>
        get() = _currentStep

    private val _numberStep = MutableLiveData<Int>()
    val numberStep: LiveData<Int>
        get() = _numberStep

    // todo не работает когда 1 шаг всего
    val nextStepVisible: LiveData<Boolean> = Transformations.map(_numberStep) { numberOfStep ->
        numberOfStep != steps.value?.size
    }

    init {
        initializeRecipe()
        _numberStep.value = 1
    }

    private fun initializeRecipe() {
        uiScope.launch {
            steps.value = getStepsDatabase()
        }
    }

    private suspend fun getStepsDatabase(): ArrayList<Step?>? {
        return withContext(Dispatchers.IO) {
            dao.getStepsByRecipeId(recipeId) as ArrayList<Step?>
        }
    }

    fun refreshCurrentStep() {
        // берем -1 потому что наши шаги начинаются с 0, а сами шаги считаются с 1
        _currentStep.value = _numberStep.value?.let { steps.value?.get(it - 1) }
    }

    fun decrementStep() {
        if (_numberStep.value != 1) {
            _numberStep.value = (_numberStep.value)?.minus(1)
            refreshCurrentStep()
        }
    }

    fun incrementStep() {
        _numberStep.value = (_numberStep.value)?.plus(1)
        refreshCurrentStep()
    }


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
