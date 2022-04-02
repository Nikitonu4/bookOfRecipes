package com.example.bookofrecipes.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "ingredients_table",  foreignKeys = [
    ForeignKey(
        entity = Recipe::class,
        parentColumns = arrayOf("recipe_id"),
        childColumns = arrayOf("recipe_id"),
        onDelete = ForeignKey.CASCADE
    )
])
data class Ingredient(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ingredient_id")
    val ingredientId: Long = 0L,

    @ColumnInfo(name = "title")
    var title: String =  "",

    @ColumnInfo(name = "amount")
    var amount: String =  "",

    // временное, чтобы проверить хотя бы один ко многим
    @ColumnInfo(name = "recipe_id")
    var recipeId: Long = 0L,
)