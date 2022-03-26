package com.example.bookofrecipes.addrecipe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.bookofrecipes.R
import com.example.bookofrecipes.data.database.BookOfRecipeDatabase
import com.example.bookofrecipes.databinding.AddRecipeFragmentBinding
import com.example.bookofrecipes.recipes.RecipesFragmentDirections

class AddRecipeFragment : Fragment() {
    private lateinit var viewModel: AddRecipeViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Get a reference to the binding object and inflate the fragment views.
        val binding: AddRecipeFragmentBinding = DataBindingUtil.inflate(
            inflater, R.layout.add_recipe_fragment, container, false)

        val application = requireNotNull(this.activity).application
        val dao = BookOfRecipeDatabase.getInstance(application).getRecipeDao()

        val viewModelFactory = AddRecipeViewModelFactory(dao, application)

        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(AddRecipeViewModel::class.java)
        binding.addRecipeButton.setOnClickListener {
            // todo проверка на данные

            viewModel.onSave(binding.addRecipeTitle.text.toString())

        }
//        val dao = BookOfRecipeDatabase.getInstance(application).getRecipeDao()
//        val viewModelFactory = AddRecipeViewModelFactory(dao, application)
//        viewModel = ViewModelProvider(this, viewModelFactory)
//            .get(AddRecipeViewModel::class.java)
//
//        val adapter = AddRecipesAdapter()
//        binding.recipesList.adapter = adapter

//        viewModel.recipes.observe(viewLifecycleOwner, Observer { recipes ->
//            if (recipes != null)
//                adapter.data = recipes
//        })

//        viewModel.navigateToRecipe.observe(viewLifecycleOwner, Observer { recipe ->
//            if (recipe != null) {
//                this.findNavController().navigate(
//                    SleepTrackerFragmentDirections
//                        .actionSleepTrackerFragmentToSleepQualityFragment(night.nightId))
//                viewModel.doneNavigating()
//            }
//        })


        return binding.root
    }
}