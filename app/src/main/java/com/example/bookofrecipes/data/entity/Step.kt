package com.example.bookofrecipes.data.entity

import androidx.room.*

@Entity(tableName = "steps_table",  foreignKeys = [
    ForeignKey(
        entity = Recipe::class,
        parentColumns = arrayOf("recipe_id"),
        childColumns = arrayOf("recipe_id"),
        onDelete = ForeignKey.CASCADE
    ),
])
data class Step(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "step_id")
    var stepId: Long = 0L,

    @ColumnInfo(name = "description")
    val description: String =  "",

    @ColumnInfo(name = "number_of_step")
    val numberOfStep: Int =  1,

    @ColumnInfo(name = "recipe_id", index = true)
    val recipeId: Long = 0L,

)