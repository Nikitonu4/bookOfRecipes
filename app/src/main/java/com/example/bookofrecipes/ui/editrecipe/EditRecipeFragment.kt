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


class EditRecipeFragment : Fragment() {
    private lateinit var viewModel: EditRecipeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Get a reference to the binding object and inflate the fragment views.
        val binding: EditRecipeFragmentBinding = DataBindingUtil.inflate(
            inflater, R.layout.edit_recipe_fragment, container, false
        )

        val application = requireNotNull(this.activity).application
        val dao = BookOfRecipeDatabase.getInstance(application).getRecipeDao()

        val viewModelFactory = EditRecipeViewModelFactory(dao, application)

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

//        // LISTENERS PART
//        binding.addStepButton.setOnClickListener {
//            viewModel.steps.add(Step())
//            if (viewModel.steps.size != 0) {
//                binding.viewSteps.visibility = View.VISIBLE
//            }
//            stepsAdapter.data = viewModel.steps
//        }
//
//        // todo некорректно работает добавление из-за adapter?
//        binding.addIngredientButton.setOnClickListener {
//            viewModel.ingredients.add(Ingredient())
//            if (viewModel.ingredients.size != 0) {
//                binding.viewIngredients.visibility = View.VISIBLE
//            }
//            ingredientAdapter.data = viewModel.ingredients
//        }
//
//        binding.editRecipeButton.setOnClickListener {
//            val title = binding.editRecipeTitle.text.toString()
//            if (title.isEmpty()) {
//                val error: String = application.resources.getString(R.string.error_empty_title)
//                binding.editRecipeTitle.setError(error);
//                binding.editRecipeTitle.requestFocus();
//            } else {
//                viewModel.existRecipe(title)
//            }
//        }
//
//
//        // OBSERVE PART
//
//        // как только сохранится рецепт - закидываем шаги
//        viewModel.recipeId.observe(viewLifecycleOwner, Observer { recipeId ->
//            if (recipeId != -1L) {
//                viewModel.onSaveSteps(recipeId)
//                viewModel.onSaveIngredients(recipeId)
//            }
//        })
//
//        viewModel.isExistRecipe.observe(viewLifecycleOwner, Observer { isExistRecipe ->
//            if (isExistRecipe == true) {
//                val error: String = application.resources.getString(R.string.error_unque_title)
//                binding.editRecipeTitle.setError(error);
//                binding.editRecipeTitle.requestFocus();
//            }
//        })
//
//        viewModel.navigateAfterNewRecipe.observe(viewLifecycleOwner, Observer { navigate ->
//            if (navigate!!) {
//                this.findNavController().navigateUp()
//                viewModel.doneNavigating()
//            }
//        })
//
//        viewModel.canCreateNewRecipe.observe(viewLifecycleOwner, Observer { canCreateNewRecipe ->
//            if (canCreateNewRecipe == true) {
//                viewModel.onSaveRecipe(
//                    binding.editRecipeTitle.text.toString(),
//                )
//            }
//        })

        return binding.root
    }
}