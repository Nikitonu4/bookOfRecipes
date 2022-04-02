/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.bookofrecipes.ui.steps


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.bookofrecipes.R
import com.example.bookofrecipes.data.database.BookOfRecipeDatabase
import com.example.bookofrecipes.data.entity.Ingredient
import com.example.bookofrecipes.databinding.StepsFragmentBinding
import com.example.bookofrecipes.databinding.TheRecipeFragmentBinding
import com.example.bookofrecipes.ui.therecipe.TheRecipeFragmentArgs
import com.example.bookofrecipes.ui.therecipe.TheRecipeViewModel
import com.example.bookofrecipes.ui.therecipe.TheRecipeViewModelFactory

class StepsFragment : Fragment() {

    private lateinit var viewModel: StepsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Get a reference to the binding object and inflate the fragment views.
        val binding: StepsFragmentBinding = DataBindingUtil.inflate(
            inflater, R.layout.steps_fragment, container, false
        )
        val application = requireNotNull(this.activity).application
        val args = StepsFragmentArgs.fromBundle(requireArguments())
        val dao = BookOfRecipeDatabase.getInstance(application).getRecipeDao()
        val viewModelFactory = StepsViewModelFactory(args.recipeId, dao, application)
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(StepsViewModel::class.java)
//        binding.nextStepButton.setOnClickListener {
////            viewModel.onCorrect()
//        }
//        binding.prevButton.setOnClickListener {
//            viewModel.onSkip()
//        }

        viewModel.steps.observe(viewLifecycleOwner, Observer { steps ->
            if (steps != null) {
                viewModel.setCurrentStep(0)
            }
        })

        viewModel.currentStep.observe(viewLifecycleOwner, Observer { step ->
            binding.currentStep.text = step.description
        })
//
//        viewModel.score.observe(viewLifecycleOwner, Observer { newScore ->
//            binding.scoreText.text = newScore.toString()
//        })
//
//        viewModel.currentTime.observe(viewLifecycleOwner, Observer { newTime ->
//            binding.timerText.text = DateUtils.formatElapsedTime(newTime)
//
//        })
//
//        // Sets up event listening to navigate the player when the game is finished
//        viewModel.eventGameFinish.observe(viewLifecycleOwner, Observer { isFinished ->
//            if (isFinished) {
//                val currentScore = viewModel.score.value ?: 0
//                val action = GameFragmentDirections.actionGameToScore(currentScore)
//                findNavController().navigate(action)
//                viewModel.onGameFinishComplete()
//            }
//        })

        return binding.root

    }

}
