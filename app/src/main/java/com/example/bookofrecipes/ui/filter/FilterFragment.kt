package com.example.bookofrecipes.ui.filter

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
import com.example.bookofrecipes.databinding.FilterListFragmentBinding

class FilterFragment : Fragment() {
    private lateinit var viewModel: FilterViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FilterListFragmentBinding = DataBindingUtil.inflate(
            inflater, R.layout.filter_list_fragment, container, false
        )

        val application = requireNotNull(this.activity).application
        val dao = BookOfRecipeDatabase.getInstance(application).getRecipeDao()
        val viewModelFactory = FilterViewModelFactory(dao, application)
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(FilterViewModel::class.java)

        val adapter = FilterAdapter()
        binding.filterIngredientsList.adapter = adapter
        adapter.viewModel = viewModel

        viewModel.ingredients.observe(viewLifecycleOwner, Observer { ingredients ->
            if (ingredients != null)
                adapter.data = ingredients
        })

        binding.goFilterButton.setOnClickListener {
//            Data.filter = viewModel.chooseIngredients
//            if (viewModel.chooseIngredients.isEmpty()) {
//                Data.filter = null
//            }
            this.findNavController().navigateUp()
        }

//        viewModel.navigateToRecipe.observe(viewLifecycleOwner, Observer { recipe ->
//            if (recipe != null) {
//                this.findNavController().navigate(
//                    RecipesFragmentDirections
//                        .actionRecipesFragmentToTheRecipeFragment(recipe.recipeId)
//                )
//                viewModel.doneNavigating()
//            }
//        })

//        binding.addRecipe.setOnClickListener {
//            findNavController().navigate(RecipesFragmentDirections.actionRecipesFragmentToAddRecipeFragment())
//        }


        return binding.root
    }
}