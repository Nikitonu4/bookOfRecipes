package com.example.bookofrecipes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.example.bookofrecipes.data.dao.BookOfRecipesDatabaseDao
import com.example.bookofrecipes.data.database.BookOfRecipeDatabase
import com.example.bookofrecipes.data.entity.Ingredient
import com.example.bookofrecipes.data.entity.Recipe
import com.example.bookofrecipes.data.entity.Step
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }
}