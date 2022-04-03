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
import androidx.navigation.ui.navigateUp
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
            var data = ""
            if (viewModel.chooseIngredients.isNotEmpty()) {
                data = viewModel.chooseIngredients.joinToString(separator = ",")
            }

            // штука для сохранения default value в navigation
            val actionDetail = FilterFragmentDirections.actionFilterFragmentToRecipesFragment()
            actionDetail.filtredIngredients = data
            this.findNavController().navigate(actionDetail)
   }

        return binding.root
    }
}