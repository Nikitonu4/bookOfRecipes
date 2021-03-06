package com.example.bookofrecipes.ui.recipes

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.bookofrecipes.R
import com.example.bookofrecipes.data.entity.Recipe

class RecipesViewHolder private constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val recipeTitle: TextView = itemView.findViewById(R.id.recipeTitle)

    val deleteRecipeButton: ImageButton = itemView.findViewById(R.id.deleteRecipeButton)
    val editRecipeButton: ImageButton = itemView.findViewById(R.id.editRecipeButton)
    val recipeItem: ConstraintLayout = itemView.findViewById(R.id.recipeItem)

    fun bind(item: Recipe) {
        recipeTitle.text = item.title
    }

    companion object {
        fun from(parent: ViewGroup): RecipesViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater
                .inflate(R.layout.item_recipe_fragment, parent, false)
            return RecipesViewHolder(view)
        }
    }
}

class RecipesAdapter : RecyclerView.Adapter<RecipesViewHolder>() {

    lateinit var viewModel: RecipesViewModel

    var data = listOf<Recipe>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: RecipesViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
        holder.recipeItem.setOnClickListener {
            viewModel.openRecipeInfo(item)
        }
        holder.deleteRecipeButton.setOnClickListener {
            viewModel.deleteRecipe(item)
        }
        holder.editRecipeButton.setOnClickListener { view ->
            view.findNavController().navigate(RecipesFragmentDirections.actionRecipesFragmentToEditRecipeFragment(item.recipeId))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipesViewHolder {
        return RecipesViewHolder.from(parent)
    }
}

