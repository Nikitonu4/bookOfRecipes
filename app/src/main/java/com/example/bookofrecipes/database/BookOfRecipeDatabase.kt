/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.bookofrecipes.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.bookofrecipes.database.ingredients.Ingredient
import com.example.bookofrecipes.database.ingredients.IngredientDatabaseDao
import com.example.bookofrecipes.database.recipes.Recipe
import com.example.bookofrecipes.database.recipes.RecipeDatabaseDao

@Database(entities = [Recipe::class, Ingredient::class], version = 1, exportSchema = false)
abstract class BookOfRecipeDatabase : RoomDatabase() {

    abstract fun getRecipeDatabaseDao(): RecipeDatabaseDao
    abstract fun getIngredientDatabaseDao(): IngredientDatabaseDao

    companion object {
        @Volatile
        private var INSTANCE: BookOfRecipeDatabase? = null

        fun getInstance(context: Context): BookOfRecipeDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(context.applicationContext,
                            BookOfRecipeDatabase::class.java, "book_of_recipes_db")
                            .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}