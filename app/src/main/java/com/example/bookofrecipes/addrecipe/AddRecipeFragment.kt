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
import com.example.bookofrecipes.data.entity.Step
import com.example.bookofrecipes.databinding.AddRecipeFragmentBinding


class AddRecipeFragment : Fragment() {
    private lateinit var viewModel: AddRecipeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Get a reference to the binding object and inflate the fragment views.
        val binding: AddRecipeFragmentBinding = DataBindingUtil.inflate(
            inflater, R.layout.add_recipe_fragment, container, false
        )

        val application = requireNotNull(this.activity).application
        val dao = BookOfRecipeDatabase.getInstance(application).getRecipeDao()

        val viewModelFactory = AddRecipeViewModelFactory(dao, application)

        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(AddRecipeViewModel::class.java)

        val stepsAdapter = AddStepsAdapter()
        stepsAdapter.data = viewModel.steps
        stepsAdapter.viewModel = viewModel
        binding.stepsList.adapter = stepsAdapter


        binding.addStepButton.setOnClickListener {
            viewModel.steps.add(Step())
            stepsAdapter.data = viewModel.steps
        }

        binding.addRecipeButton.setOnClickListener {
            // todo проверка на данные
            val title = binding.addRecipeTitle.text.toString()
            if(title.isEmpty()){
                val error: String = application.resources.getString(R.string.error_empty_title)
                binding.addRecipeTitle.setError(error);
                binding.addRecipeTitle.requestFocus();
            }
            else{
                viewModel.existRecipe(title)
            }
        }


        // OBSERVE PART

        // как только сохранится рецепт - закидываем шаги
        viewModel.recipeId.observe(viewLifecycleOwner, Observer { id ->
            if (id != -1L) {
                viewModel.onSaveSteps(id)
            }
        })

        viewModel.isExistRecipe.observe(viewLifecycleOwner, Observer { isExistRecipe ->
            if (isExistRecipe == true) {
                val error: String = application.resources.getString(R.string.error_unque_title)
                binding.addRecipeTitle.setError(error);
                binding.addRecipeTitle.requestFocus();
            }
        })

        viewModel.navigateAfterNewRecipe.observe(viewLifecycleOwner, Observer { navigate ->
            if (navigate!!) {
                this.findNavController().navigateUp()
                viewModel.finishNavigate()
            }
        })

        viewModel.canCreateNewRecipe.observe(viewLifecycleOwner, Observer { canCreateNewRecipe ->
            if (canCreateNewRecipe == true) {
                viewModel.onSaveRecipe(
                    binding.addRecipeTitle.text.toString(),
                )
            }
        })

        return binding.root
    }
}