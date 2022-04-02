package com.example.bookofrecipes.ui.recipes

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
import com.example.bookofrecipes.databinding.RecipesListFragmentBinding

class RecipesFragment : Fragment() {
    private lateinit var viewModel: RecipesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: RecipesListFragmentBinding = DataBindingUtil.inflate(
            inflater, R.layout.recipes_list_fragment, container, false
        )

        val application = requireNotNull(this.activity).application
        val dao = BookOfRecipeDatabase.getInstance(application).getRecipeDao()
        val viewModelFactory = RecipesViewModelFactory(dao, application)
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(RecipesViewModel::class.java)

        val adapter = RecipesAdapter()
        binding.recipesList.adapter = adapter
        adapter.viewModel = viewModel

        viewModel.recipes.observe(viewLifecycleOwner, Observer { recipes ->
            if (recipes != null)
                adapter.data = recipes
        })

        viewModel.navigateToRecipe.observe(viewLifecycleOwner, Observer { recipe ->
            if (recipe != null) {
                this.findNavController().navigate(
                    RecipesFragmentDirections
                        .actionRecipesFragmentToTheRecipeFragment(recipe.recipeId)
                )
                viewModel.doneNavigating()
            }
        })

        binding.addRecipe.setOnClickListener {
            findNavController().navigate(RecipesFragmentDirections.actionRecipesFragmentToAddRecipeFragment())
        }

        binding.filterButton.setOnClickListener {
            findNavController().navigate(RecipesFragmentDirections.actionRecipesFragmentToFilterFragment())
        }

        return binding.root
    }
}