package com.example.lab_04_00073824.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.lab_04_00073824.view.HomeScreen
import com.example.lab_04_00073824.view.TaskScreen
import com.example.lab_04_00073824.viewmodel.TaskViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val viewModel: TaskViewModel = viewModel()


    NavHost(
        navController = navController,
        startDestination = Home)
    {
        composable<Home> {
            HomeScreen(navController = navController)
        }

        composable<Task> {
            TaskScreen(viewModel = viewModel)
        }
    }

}