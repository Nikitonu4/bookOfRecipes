package com.example.bookofrecipes.ui.addrecipe

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
import com.example.bookofrecipes.data.entity.Ingredient
import com.example.bookofrecipes.data.entity.Step
import com.example.bookofrecipes.databinding.AddRecipeFragmentBinding


class AddRecipeFragment : Fragment() {
    private lateinit var viewModel: AddRecipeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: AddRecipeFragmentBinding = DataBindingUtil.inflate(
            inflater, R.layout.add_recipe_fragment, container, false
        )

        val application = requireNotNull(this.activity).application
        val dao = BookOfRecipeDatabase.getInstance(application).getRecipeDao()

        val viewModelFactory = AddRecipeViewModelFactory(dao, application)

        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(AddRecipeViewModel::class.java)

        val stepsAdapter = AddStepsAdapter()
        stepsAdapter.viewModel = viewModel
        binding.stepsList.adapter = stepsAdapter
        stepsAdapter.data = viewModel.steps

        val ingredientAdapter = AddIngredientAdapter()
        ingredientAdapter.viewModel = viewModel
        binding.ingredientsList.adapter = ingredientAdapter
        ingredientAdapter.data = viewModel.ingredients

        // LISTENERS PART
        binding.addStepButton.setOnClickListener {
            viewModel.steps.add(Step())
            if (viewModel.steps.size != 0) {
                binding.viewSteps.visibility = View.VISIBLE
            }
            stepsAdapter.data = viewModel.steps
        }

        // todo некорректно работает добавление из-за adapter?
        binding.addIngredientButton.setOnClickListener {
            viewModel.ingredients.add(Ingredient())
            if (viewModel.ingredients.size != 0) {
                binding.viewIngredients.visibility = View.VISIBLE
            }
            ingredientAdapter.data = viewModel.ingredients
        }

        binding.addRecipeButton.setOnClickListener {
            val title = binding.addRecipeTitle.text.toString()
            when {
                title.isEmpty() -> {
                    val error: String = application.resources.getString(R.string.error_empty_title)
                    binding.addRecipeTitle.setError(error);
                    binding.addRecipeTitle.requestFocus();
                }
                viewModel.steps.size < 3 -> {
                    val error: String = application.resources.getString(R.string.error_empty_steps)
                    binding.addRecipeTitle.setError(error);
                    binding.addRecipeTitle.requestFocus()
                }
                viewModel.ingredients.isNullOrEmpty() -> {
                    val error: String =
                        application.resources.getString(R.string.error_empty_ingredients)
                    binding.addRecipeTitle.setError(error);
                    binding.addRecipeTitle.requestFocus()
                }
                else -> viewModel.existRecipe(title)
            }
        }

        binding.backButton.setOnClickListener {
            findNavController().navigateUp()
        }


        // OBSERVE PART

        // как только сохранится рецепт - закидываем шаги
        viewModel.recipeId.observe(viewLifecycleOwner, Observer { recipeId ->
            if (recipeId != -1L) {
                viewModel.onSaveSteps(recipeId)
                viewModel.onSaveIngredients(recipeId)
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
                viewModel.doneNavigating()
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