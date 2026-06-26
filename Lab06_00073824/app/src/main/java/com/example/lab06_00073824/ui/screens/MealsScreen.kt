package com.example.lab06_00073824.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lab06_00073824.viewmodel.MealViewModel

@Composable
@Preview
fun MealScreen(viewModel: MealViewModel = viewModel()) {
    LaunchedEffect(Unit) {
        viewModel.loadMeals()
    }

    when {

        viewModel.isLoading -> {

            CircularProgressIndicator()
        }

        else -> {

            Scaffold() { padding ->

                Column(
                    modifier = Modifier.fillMaxSize().padding(25.dp)
                ) {

                    Text(
                        fontWeight = FontWeight.Bold,
                        fontStyle = FontStyle.Italic,
                        fontSize = 20.sp,
                        text = "MEALS"
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                LazyColumn (
                    modifier = Modifier
                    .fillMaxSize()
                ){

                    items(viewModel.meals) { meal ->

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)

                        ) {

                            Column(
                                modifier = Modifier.padding(16.dp)
                            ) {

                                Text(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 20.sp,
                                    text = meal.strMeal
                                )

                                Text(
                                    text = "ID: " + meal.idMeal.toString()

                                )

                                Text(
                                    text = "Categoria: " + meal.strCategory
                                )


                                Text(
                                    text = "URL: " + meal.strMealThumb
                                )
                            }
                        }
                    }
                }
            }
        }
    }
        }
}