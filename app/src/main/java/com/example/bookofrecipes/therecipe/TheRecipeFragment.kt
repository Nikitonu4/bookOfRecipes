package com.example.bookofrecipes.therecipe

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
import com.example.bookofrecipes.databinding.TheRecipeFragmentBinding

class TheRecipeFragment : Fragment() {
    private lateinit var viewModel: TheRecipeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Get a reference to the binding object and inflate the fragment views.
        val binding: TheRecipeFragmentBinding = DataBindingUtil.inflate(
            inflater, R.layout.the_recipe_fragment, container, false
        )
        val application = requireNotNull(this.activity).application

        val args = TheRecipeFragmentArgs.fromBundle(requireArguments())

        val dao = BookOfRecipeDatabase.getInstance(application).getRecipeDao()
        val viewModelFactory = TheRecipeViewModelFactory(args.recipeId, dao, application)
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(TheRecipeViewModel::class.java)
        viewModel.initializeRecipe()
//        binding.theRecipeTitle.text = viewModel.recipe.title

//        val adapter = TheRecipeAdapter()
//        binding.recipesList.adapter = adapter

        viewModel.shouldBindView.observe(viewLifecycleOwner, Observer { bind ->
            if (bind!!){
                binding.theRecipeTitle.text = viewModel.recipe.title
            }
            viewModel.bindDone()
        })

        return binding.root
    }
}