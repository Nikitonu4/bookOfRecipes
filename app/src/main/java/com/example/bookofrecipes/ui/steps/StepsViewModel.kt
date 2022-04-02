package com.example.bookofrecipes.ui.steps


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.bookofrecipes.data.dao.BookOfRecipesDatabaseDao
import com.example.bookofrecipes.data.entity.Step
import kotlinx.coroutines.*

class StepsViewModel(
    val recipeId: Long,
    val dao: BookOfRecipesDatabaseDao,
    application: Application
) : AndroidViewModel(application) {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _maxSteps = MutableLiveData<Int>()
    val maxSteps: LiveData<Int>
        get() = _maxSteps

    // The current step
    private val _currentStep = MutableLiveData<Step>()
    val currentStep: LiveData<Step>
        get() = _currentStep

     var stepList = ArrayList<Step?>()

    init {
        initializeSteps()
//        nextWord()
        _currentStep.value = stepList[0]
    }

    private fun initializeSteps() {
        uiScope.launch {
            stepList = getStepsFromDatabase()
        }
    }

    private suspend fun getStepsFromDatabase(): ArrayList<Step?> {
        return withContext(Dispatchers.IO) {
             dao.getStepsByRecipeId(recipeId) as ArrayList<Step?>
        }
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
