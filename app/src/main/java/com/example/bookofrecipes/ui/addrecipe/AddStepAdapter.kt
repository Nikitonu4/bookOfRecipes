package com.example.bookofrecipes.ui.addrecipe

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.example.bookofrecipes.R
import com.example.bookofrecipes.data.entity.Step

class AddStepsViewHolder private constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val res: Resources = itemView.context.resources
    private val stepDescription: EditText = itemView.findViewById(R.id.stepDescription)
    val deleteStepButton: ImageView = itemView.findViewById(R.id.deleteStepButton)

    fun bind(item: Step) {
        stepDescription.setText(item.description)
        stepDescription.doAfterTextChanged { item.description = stepDescription.text.toString() }
    }

    companion object {
        fun from(parent: ViewGroup): AddStepsViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater
                .inflate(R.layout.item_add_step_fragment, parent, false)
            return AddStepsViewHolder(view)
        }
    }
}

class AddStepsAdapter : RecyclerView.Adapter<AddStepsViewHolder>() {

    var data = listOf<Step>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = data.size
    lateinit var viewModel: AddRecipeViewModel

    override fun onBindViewHolder(holder: AddStepsViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
        holder.deleteStepButton.setOnClickListener {
            viewModel.steps.remove(item)
            data = viewModel.steps
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddStepsViewHolder {
        return AddStepsViewHolder.from(parent)
    }
}

