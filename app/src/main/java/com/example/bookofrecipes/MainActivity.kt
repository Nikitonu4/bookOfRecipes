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

        val dao = BookOfRecipeDatabase.getInstance(this).getRecipeDao()

        val recipes = listOf(
            Recipe(1, "Суп-пюре из запеченных овощей"),
        )

        val ingredients = listOf(
            Ingredient(1, "морковь", "1шт", 1),
            Ingredient(2, "лук репчатый", "1шт", 1),
            Ingredient(3, "чеснок", "2 зубчика", 1),
            Ingredient(4, "соль", "1 щепотка", 1),
            Ingredient(5, "тыква", "300г", 1),
        )

        val steps = listOf(
            Step(
                1,
                "Набор овощей можно варьироват по своему вкусу. Для этого супа дополнительно подойдут кабачки, томаты, баклажаны, фасоль, лук-порей, цветная капуста, брокколи, зеленый горошек и т.д. и т.п.",
                1,
                1
            ),
            Step(
                2,
                "Морковь и тыкву нарезать средними дольками. Лук очистить и нарезать не слишком мелко, зубчики чеснока очистить и оставить целыми или раздавить ножом. Перец оставляем целым, так его будет легче освободить от кожицы после запекания. Сложить овощи в форму для запекания, сбрызнуть оливковым маслом, сверху выложить свежий тимьян. Отправить в духовку, предварительно разогретую до 180 градусов.",
                2,
                1
            ),
            Step(3, "Запечь овощи до мягкости (20-25 минут).", 3, 1),
            Step(4, "Перец очистить от кожицы и семян, все овощи переложить в кастрюлю.", 4, 1),
            Step(
                5,
                "Пюрировать при помощи погружного блендера. Можно пробить до гладкости, можно оставить небольшие кусочки.",
                5,
                1
            ),
            Step(
                6,
                "Отрегулировать густоту супа по своему вкусу, добавляя воду или овощной бульон. Довести до кипения, приправить солью и перцем. Прогреть на слабом огне 5-7 минут.",
                6,
                1
            ),
            Step(7, "Подавать, посыпав тыквенными семечками, семенем льна или кунжутом.", 7, 1),
            Step(8, "Любителям острого подать с колечками жгучего перца и с зеленым луком.", 8, 1),
        )
//        lifecycleScope.launch {
//            recipes.forEach { dao.insertRecipe(it) }
//            ingredients.forEach { dao.insertIngredient(it) }
//            steps.forEach { dao.insertStep(it) }
//        }
    }

}