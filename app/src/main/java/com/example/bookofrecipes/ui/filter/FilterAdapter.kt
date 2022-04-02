package com.example.bookofrecipes.ui.filter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bookofrecipes.R

class FilterViewHolder private constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val filterIngredientTitle: TextView = itemView.findViewById(R.id.filterIngredientTitle)
    private val filterCheckbox: CheckBox = itemView.findViewById(R.id.filterCheckbox)

    fun bind(item: String, viewModel: FilterViewModel) {
        filterIngredientTitle.text = item
        filterCheckbox.setOnCheckedChangeListener { _, checked ->
            if (checked) {
                viewModel.chooseIngredients.add(item)
            } else {
                viewModel.chooseIngredients.remove(item)
            }
        }
    }

    companion object {
        fun from(parent: ViewGroup): FilterViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater
                .inflate(R.layout.item_filter_fragment, parent, false)
            return FilterViewHolder(view)
        }
    }
}

class FilterAdapter : RecyclerView.Adapter<FilterViewHolder>() {

    lateinit var viewModel: FilterViewModel

    var data = listOf<String>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: FilterViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item, viewModel)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterViewHolder {
        return FilterViewHolder.from(parent)
    }
}

