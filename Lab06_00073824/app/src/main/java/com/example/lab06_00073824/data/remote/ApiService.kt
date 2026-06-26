package com.example.lab06_00073824.data.remote

import com.example.lab06_00073824.data.model.Meals
import retrofit2.http.GET

interface ApiService {

    @GET("https://www.themealdb.com/api/json/v1/1/search.php?s=")
    suspend fun getMeals(): Meals

}