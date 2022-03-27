package com.example.bookofrecipes.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ingredients_table",  foreignKeys = [
])
data class Ingredient(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ingredient_id")
    val ingredientId: Long = 0L,

    @ColumnInfo(name = "title")
    var title: String =  "",

)