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
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.bookofrecipes.data.entity.Ingredient
import com.example.bookofrecipes.data.entity.IngredientCount

@Dao
interface IngredientCountDatabaseDao {

    @Insert
    fun insert(ingredientCount: IngredientCount)

    @Update
    fun update(ingredientCount: IngredientCount)

    @Query("SELECT * FROM ingredients_count_table WHERE ingredient_count_id = :key")
    fun get(key: Long): IngredientCount?

    @Query("DELETE FROM ingredients_count_table")
    fun clear()

    @Query("SELECT * FROM ingredients_count_table ORDER BY ingredient_count_id DESC")
    fun getAllIngredientCount(): LiveData<List<IngredientCount>>

}
