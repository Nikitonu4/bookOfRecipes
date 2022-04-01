package com.example.bookofrecipes.therecipe

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bookofrecipes.R
import com.example.bookofrecipes.data.entity.Recipe

class TheRecipeViewHolder private constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val res: Resources = itemView.context.resources
    private val recipeTitle: TextView = itemView.findViewById(R.id.theRecipeTitle)

    fun bind(item: Recipe) {
        recipeTitle.text = item.title
    }

    companion object {
        fun from(parent: ViewGroup): TheRecipeViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater
                .inflate(R.layout.recipe_item_fragment, parent, false)
            return TheRecipeViewHolder(view)
        }
    }
}

class TheRecipeAdapter : RecyclerView.Adapter<TheRecipeViewHolder>() {

    var data = listOf<Recipe>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: TheRecipeViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TheRecipeViewHolder {
        return TheRecipeViewHolder.from(parent)
    }
}

