package com.example.bookofrecipes.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "ingredients_count_table",   foreignKeys = [
//    ForeignKey(
//        entity = Ingredient::class,
//        parentColumns = arrayOf("ingredient_id"),
//        childColumns = arrayOf("ingredient_id"),
//        onDelete = ForeignKey.CASCADE
//    ),
//    ForeignKey(
//        entity = Recipe::class,
//        parentColumns = arrayOf("recipe_id"),
//        childColumns = arrayOf("recipe_id"),
//        onDelete = ForeignKey.CASCADE
//    )
])
data class IngredientCount(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ingredient_count_id")
    var ingredientCountId: Long = 0L,

    @ColumnInfo(name = "count")
    val count: Int =  0,

//    @ColumnInfo(name = "ingredient_id", index = true)
//    var ingredientId: Long = 0L,
//
//    @ColumnInfo(name = "recipe_id", index = true)
//    var recipeId: Long = 0L,
)