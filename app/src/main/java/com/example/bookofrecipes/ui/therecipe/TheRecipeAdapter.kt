package com.example.bookofrecipes.ui.therecipe

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bookofrecipes.R
import com.example.bookofrecipes.data.entity.Ingredient

class TheRecipeViewHolder private constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val res: Resources = itemView.context.resources
    private val ingredientTitle: TextView = itemView.findViewById(R.id.needIngredientTitle)
    private val needIngredientAmount: TextView = itemView.findViewById(R.id.needIngredientAmount)

    fun bind(item: Ingredient) {
        ingredientTitle.text = item.title
        needIngredientAmount.text = item.amount
    }

    companion object {
        fun from(parent: ViewGroup): TheRecipeViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater
                .inflate(R.layout.need_ingredient_fragment, parent, false)
            return TheRecipeViewHolder(view)
        }
    }
}

class TheRecipeAdapter : RecyclerView.Adapter<TheRecipeViewHolder>() {
    var data = listOf<Ingredient>()
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