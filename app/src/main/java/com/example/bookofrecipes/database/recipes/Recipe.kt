package com.example.bookofrecipes.database.recipes

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipes_table")
data class Recipe(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var recipeId: Long = 0L,

    @ColumnInfo(name = "title")
    val title: String =  "",

//    @ColumnInfo(name = "steps")
//    var steps: ArrayList<String> = ArrayList(),
)