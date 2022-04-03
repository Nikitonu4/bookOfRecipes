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
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import com.example.bookofrecipes.data.entity.Ingredient
import com.example.bookofrecipes.data.entity.Recipe
import com.example.bookofrecipes.data.entity.Step

@Dao
interface BookOfRecipesDatabaseDao {

    @Insert()
    fun insertRecipe(recipe: Recipe)

    @Insert()
    fun insertStep(step: Step)

    @Insert()
    fun insertIngredient(ingredient: Ingredient)

    @Insert()
    fun bulkInsertSteps(steps: List<Step>): List<Long>

    @Insert()
    fun bulkInsertIngredients(ingredients: List<Ingredient>): List<Long>

    @Query("SELECT * FROM recipes_table ORDER BY recipe_id DESC")
    fun getAllRecipes(): LiveData<List<Recipe>>

    @Query("SELECT DISTINCT LOWER(title) FROM ingredients_table ORDER BY title")
    fun getAllIngredients(): LiveData<List<String>>

    @Query("SELECT * FROM ingredients_table WHERE recipe_id = :key")
    fun getIngredientsByRecipeId(key: Long): List<Ingredient>

    @Query("SELECT * FROM steps_table WHERE recipe_id = :key ORDER BY number_of_step")
    fun getStepsByRecipeId(key: Long): List<Step>

    @Query("SELECT * FROM recipes_table WHERE title = :key")
    fun getRecipeByTitle(key: String): Recipe?

    @Query("SELECT * FROM recipes_table WHERE recipe_id = :key")
    fun getRecipeById(key: Long): Recipe?

    @Delete
    fun deleteRecipe(recipe: Recipe)


    @Query("DELETE FROM ingredients_table WHERE ingredient_id in (:idList)")
    fun deleteIngredients(idList: List<Long>)

    @Query("DELETE FROM steps_table WHERE recipe_id = :key")
    fun removeStepByRecipeId(key: Long)

    @Update
    fun updateRecipe(recipe: Recipe)

    @Update
    fun updateIngredient(ingredient: Ingredient)

    @Update
    fun updateStep(step: Step)

    @Query("SELECT * FROM ingredients_table it JOIN recipes_table rt ON it.recipe_id = rt.recipe_id WHERE LOWER(it.title) IN (:titles)")
    fun getRecipeFilterIngredients(titles: List<String>): List<Recipe>

}
