package com.example.bookofrecipes.data.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.bookofrecipes.data.entity.Recipe
import com.example.bookofrecipes.data.entity.Step

data class RecipeWithSteps(
    @Embedded val recipe: Recipe,
    @Relation(
        parentColumn = "recipe_id",
        entityColumn = "step_id"
    )
    val steps: List<Step>
)