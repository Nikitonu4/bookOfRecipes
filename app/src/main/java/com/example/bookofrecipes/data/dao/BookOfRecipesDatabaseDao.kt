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

package com.example.bookofrecipes.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.bookofrecipes.data.entity.Ingredient
import com.example.bookofrecipes.data.entity.Recipe
import com.example.bookofrecipes.data.entity.Step
import com.example.bookofrecipes.data.relations.RecipeWithSteps

@Dao
interface BookOfRecipesDatabaseDao {

    @Insert()
    fun insertRecipe(recipe: Recipe)

    @Insert()
    fun bulkInsertSteps(steps: List<Step>): List<Long>

    @Insert()
    fun bulkInsertIngredients(ingredients: List<Ingredient>): List<Long>

//    @Transaction
//    @Query("SELECT * FROM recipes_table WHERE recipe_id = :key")
//    fun getRecipeWithSteps(key: Long): LiveData<List<RecipeWithSteps>>

    @Query("SELECT * FROM recipes_table ORDER BY recipe_id DESC")
    fun getAllRecipes(): LiveData<List<Recipe>>

    @Query("SELECT DISTINCT LOWER(title) FROM ingredients_table ORDER BY title")
    fun getAllIngredients(): LiveData<List<String>>

    @Query("SELECT * FROM ingredients_table WHERE recipe_id = :key")
    fun getIngredientsForRecipe(key: Long): List<Ingredient>

    @Query("SELECT * FROM recipes_table WHERE title = :key")
    fun getRecipeByTitle(key: String): Recipe?

    @Query("SELECT * FROM recipes_table WHERE recipe_id = :key")
    fun getRecipeById(key: Long): Recipe?

    @Query("SELECT * FROM steps_table WHERE recipe_id = :key ORDER BY number_of_step")
    fun getStepsByRecipeId(key: Long): List<Step>

    @Delete
    fun delete(recipe: Recipe)

//    @Update
//    fun update(recipe: Recipe)
//

//
//    @Query("DELETE FROM recipes_table")
//    fun clear()
//


//    @Query(
//        "SELECT * FROM recipes_table r" +
//                " JOIN steps_table s ON r.recipe_id = s.step_id"
//    )
//    fun loadUserAndBookNames(): Map<Recipe, List<Step>>
}
