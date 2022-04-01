package com.example.bookofrecipes.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "steps_table",
    foreignKeys = [
    ForeignKey(
        entity = Recipe::class,
        parentColumns = arrayOf("recipe_id"),
        childColumns = arrayOf("recipe_id"),
        onDelete = ForeignKey.CASCADE
    ),
]
)
data class Step(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "step_id")
    val stepId: Long = 0L,

    @ColumnInfo(name = "description")
    var description: String =  "",

    @ColumnInfo(name = "number_of_step")
    var numberOfStep: Int =  1,

    @ColumnInfo(name = "recipe_id")
    var recipeId: Long = 0L,
)