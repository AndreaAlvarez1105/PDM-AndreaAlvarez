package com.example.lab06_00073824.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lab06_00073824.data.model.Meal
import com.example.lab06_00073824.data.remote.RetrofitInstance
import kotlinx.coroutines.launch

class MealViewModel : ViewModel() {

    var meals by mutableStateOf<List<Meal>>(emptyList())
        private set

    var isLoading by mutableStateOf(false)
        private set

    fun loadMeals() {

        viewModelScope.launch {

            isLoading = true

            try {
                Log.d("status","vivo")

                meals = RetrofitInstance
                    .api
                    .getMeals().meals

                Log.d("status",meals.toString())


            } catch (e: Exception) {

                e.printStackTrace()

            } finally {

                isLoading = false
            }
        }
    }

}