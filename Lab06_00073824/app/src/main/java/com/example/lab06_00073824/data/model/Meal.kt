package com.example.lab06_00073824.data.model

data class Meal (

    val idMeal: Int,
    val strMeal: String,
    val strCategory: String,
    val strArea: String,
    val strMealThumb: String

)
data class Meals(
    val meals: List<Meal>
)