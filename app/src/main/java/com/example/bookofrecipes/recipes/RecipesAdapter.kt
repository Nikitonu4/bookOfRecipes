package com.example.bookofrecipes.recipes

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bookofrecipes.R
import com.example.bookofrecipes.data.entity.Recipe

class RecipesViewHolder private constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val res: Resources = itemView.context.resources
    private val recipeTitle: TextView = itemView.findViewById(R.id.ingredient_title)

    fun bind(item: Recipe) {
        recipeTitle.text = item.title
    }

    companion object {
        fun from(parent: ViewGroup): RecipesViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater
                .inflate(R.layout.recipe_item_fragment, parent, false)
            return RecipesViewHolder(view)
        }
    }
}

class RecipesAdapter : RecyclerView.Adapter<RecipesViewHolder>() {

    var data = listOf<Recipe>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: RecipesViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipesViewHolder {
        return RecipesViewHolder.from(parent)
    }
}

