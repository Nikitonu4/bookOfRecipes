package com.example.bookofrecipes.ui.editrecipe

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
import com.example.bookofrecipes.databinding.EditRecipeFragmentBinding
import com.example.bookofrecipes.ui.therecipe.TheRecipeFragmentArgs


class EditRecipeFragment : Fragment() {
    private lateinit var viewModel: EditRecipeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: EditRecipeFragmentBinding = DataBindingUtil.inflate(
            inflater, R.layout.edit_recipe_fragment, container, false
        )

        val application = requireNotNull(this.activity).application
        val args = EditRecipeFragmentArgs.fromBundle(requireArguments())
        val dao = BookOfRecipeDatabase.getInstance(application).getRecipeDao()

        val viewModelFactory = EditRecipeViewModelFactory(args.recipeId, dao, application)

        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(EditRecipeViewModel::class.java)

        val stepsAdapter = EditStepsAdapter()
        stepsAdapter.viewModel = viewModel
        binding.editStepsList.adapter = stepsAdapter
        stepsAdapter.data = viewModel.steps

        val ingredientAdapter = EditIngredientAdapter()
        ingredientAdapter.viewModel = viewModel
        binding.editIngredientsList.adapter = ingredientAdapter
        ingredientAdapter.data = viewModel.ingredients


        viewModel.afterInit.observe(viewLifecycleOwner, Observer { init ->
            if (init == true) {
                binding.editRecipeTitle.setText(viewModel.recipe.title)

                binding.editStepsList.adapter = stepsAdapter
                stepsAdapter.data = viewModel.steps

                binding.editIngredientsList.adapter = ingredientAdapter
                ingredientAdapter.data = viewModel.ingredients

            }
        })

        // LISTENERS PART
        // todo некорректно работает изменение из-за adapter?
        binding.addStepButton.setOnClickListener {
            viewModel.steps.add(Step())
            stepsAdapter.data = viewModel.steps
        }

        binding.addIngredientButton.setOnClickListener {
            viewModel.ingredients.add(Ingredient())
            ingredientAdapter.data = viewModel.ingredients
        }

        binding.editRecipeButton.setOnClickListener {
            val title = binding.editRecipeTitle.text.toString()
            when {
                title.isEmpty() -> {
                    val error: String = application.resources.getString(R.string.error_empty_title)
                    binding.editRecipeTitle.setError(error);
                    binding.editRecipeTitle.requestFocus();
                }
                viewModel.steps.size < 3 -> {
                    val error: String = application.resources.getString(R.string.error_empty_steps)
                    binding.editRecipeTitle.setError(error);
                    binding.editRecipeTitle.requestFocus()
                }
                viewModel.ingredients.isNullOrEmpty() -> {
                    val error: String =
                        application.resources.getString(R.string.error_empty_ingredients)
                    binding.editRecipeTitle.setError(error);
                    binding.editRecipeTitle.requestFocus()
                }
                else -> viewModel.existRecipe(title)
            }
        }

        binding.backButton.setOnClickListener {
            findNavController().navigateUp()
        }

        // OBSERVE PART

        viewModel.isExistRecipe.observe(viewLifecycleOwner, Observer { isExistRecipe ->
            if (isExistRecipe == true) {
                val error: String = application.resources.getString(R.string.error_unque_title)
                binding.editRecipeTitle.setError(error);
                binding.editRecipeTitle.requestFocus();
            }
        })

        viewModel.navigateAfterEditRecipe.observe(viewLifecycleOwner, Observer { navigate ->
            if (navigate!!) {
                this.findNavController().navigateUp()
                viewModel.doneNavigating()
            }
        })

        viewModel.canEditRecipe.observe(viewLifecycleOwner, Observer { canEditRecipe ->
            if (canEditRecipe == true) {
                viewModel.onEditRecipe(
                    binding.editRecipeTitle.text.toString(),
                )
            }
        })

        return binding.root
    }
}