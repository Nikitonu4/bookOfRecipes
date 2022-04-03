package com.example.bookofrecipes.ui.editrecipe

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.example.bookofrecipes.R
import com.example.bookofrecipes.data.entity.Ingredient

class EditIngredientsViewHolder private constructor(itemView: View) :
    RecyclerView.ViewHolder(itemView) {
    private val ingredientTitle: EditText = itemView.findViewById(R.id.ingredientTitle)
    private val ingredientAmount: EditText = itemView.findViewById(R.id.ingredientAmount)
    val deleteIngredientButton: ImageView = itemView.findViewById(R.id.deleteIngredientButton)

    fun bind(item: Ingredient) {
        ingredientTitle.setText(item.title)
        ingredientTitle.doAfterTextChanged {
            item.title = ingredientTitle.text.toString() }
        ingredientAmount.setText(item.amount)
        ingredientAmount.doAfterTextChanged { item.amount = ingredientAmount.text.toString() }
    }

    companion object {
        fun from(parent: ViewGroup): EditIngredientsViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater
                .inflate(R.layout.item_add_ingredient_fragment, parent, false)
            return EditIngredientsViewHolder(view)
        }
    }
}

class EditIngredientAdapter : RecyclerView.Adapter<EditIngredientsViewHolder>() {

    var data = listOf<Ingredient>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = data.size
    lateinit var viewModel: EditRecipeViewModel

    override fun onBindViewHolder(holder: EditIngredientsViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
        holder.deleteIngredientButton.setOnClickListener {
            viewModel.ingredients.remove(item)
            data = viewModel.ingredients
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EditIngredientsViewHolder {
        return EditIngredientsViewHolder.from(parent)
    }
}

