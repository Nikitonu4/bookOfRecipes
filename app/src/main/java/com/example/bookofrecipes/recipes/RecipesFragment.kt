package com.example.bookofrecipes.recipes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.bookofrecipes.R
import com.example.bookofrecipes.database.BookOfRecipeDatabase
import com.example.bookofrecipes.databinding.RecipesListFragmentBinding

class RecipesFragment : Fragment() {
    private lateinit var viewModel: RecipesViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Get a reference to the binding object and inflate the fragment views.
        val binding: RecipesListFragmentBinding = DataBindingUtil.inflate(
            inflater, R.layout.recipes_list_fragment, container, false)

        val application = requireNotNull(this.activity).application
        val dao = BookOfRecipeDatabase.getInstance(application).getRecipeDatabaseDao()
        val viewModelFactory = RecipesViewModelFactory(dao, application)
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(RecipesViewModel::class.java)

        val adapter = RecipesAdapter()
        binding.recipesList.adapter = adapter

        viewModel.recipes.observe(viewLifecycleOwner, Observer { recipes ->
            if (recipes != null)
                adapter.data = recipes
        })

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