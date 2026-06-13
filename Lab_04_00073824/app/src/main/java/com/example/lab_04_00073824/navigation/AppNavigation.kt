package com.example.lab_04_00073824.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.lab_04_00073824.data.local.AppDatabase
import com.example.lab_04_00073824.data.repository.TaskRepository
import com.example.lab_04_00073824.view.HomeScreen
import com.example.lab_04_00073824.view.TaskScreen
import com.example.lab_04_00073824.viewmodel.TaskViewModel
import com.example.lab_04_00073824.viewmodel.TaskViewModelFactory

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    val context = LocalContext.current
    val database = AppDatabase.getDatabase(context)
    val repository = TaskRepository(database.taskDao())

    val viewModel: TaskViewModel = viewModel(
        factory = TaskViewModelFactory(repository)
    )

    NavHost(
        navController = navController,
        startDestination = Home
    ) {
        composable<Home> {
            HomeScreen(navController = navController)
        }

        composable<Task> {
            TaskScreen(viewModel = viewModel)
        }
    }
}