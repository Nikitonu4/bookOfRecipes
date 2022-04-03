package com.example.bookofrecipes.ui.editrecipe

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

class EditStepsViewHolder private constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val res: Resources = itemView.context.resources
    private val stepDescription: EditText = itemView.findViewById(R.id.stepDescription)
    val deleteStepButton: ImageView = itemView.findViewById(R.id.deleteStepButton)

    fun bind(item: Step) {
        stepDescription.setText(item.description)
        stepDescription.doAfterTextChanged { item.description = stepDescription.text.toString() }
    }

    companion object {
        fun from(parent: ViewGroup): EditStepsViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater
                .inflate(R.layout.item_add_step_fragment, parent, false)
            return EditStepsViewHolder(view)
        }
    }
}

class EditStepsAdapter : RecyclerView.Adapter<EditStepsViewHolder>() {

    var data = listOf<Step>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = data.size
    lateinit var viewModel: EditRecipeViewModel

    override fun onBindViewHolder(holder: EditStepsViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
        holder.deleteStepButton.setOnClickListener {
            viewModel.steps.remove(item)
            data = viewModel.steps
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EditStepsViewHolder {
        return EditStepsViewHolder.from(parent)
    }
}

