package com.example.bookofrecipes.database.ingredients

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.bookofrecipes.database.recipes.Recipe

@Entity(tableName = "ingredients_table",  foreignKeys = [
])
data class Ingredient(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var ingredientId: Long = 0L,

    @ColumnInfo(name = "title")
    val title: String =  "",

)