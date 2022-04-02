package com.example.bookofrecipes.ui.steps


import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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

    init {
        initializeRecipe()
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

    fun setCurrentStep(pos: Int){
        _currentStep.value = steps.value?.get(pos)
    }



    /**
     * Moves to the next word in the list
     */
    private fun nextWord() {
        //Select and remove a word from the list
//        if (wordList.isEmpty()) {
//            resetList()
//        }
//        _word.value = wordList.removeAt(0)
    }

    /** Methods for buttons presses **/

//    fun onSkip() {
//        _score.value = (_score.value)?.minus(1)
//        nextWord()
//    }
//
//    fun onCorrect() {
//        _score.value = (_score.value)?.plus(1)
//        nextWord()
//    }
//
//    /** Methods for completed events **/
//
//    fun onGameFinishComplete() {
//        _eventGameFinish.value = false
//    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
