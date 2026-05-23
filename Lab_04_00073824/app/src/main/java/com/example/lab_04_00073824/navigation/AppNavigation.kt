package com.example.lab_04_00073824.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.lab_04_00073824.screen.HomeScreen
import com.example.lab_04_00073824.screen.TaskScreen
import com.example.lab_04_00073824.viewmodel.GeneralViewModel

@Composable
fun AppNavigation() {
    val navCrontroller = rememberNavController()
    val viewModel: GeneralViewModel = viewModel()


    NavHost(
        navController = navCrontroller,
        startDestination = Home)
    {
        composable<Home> {
            HomeScreen(navController = navCrontroller)
        }

        composable<Task> {
            TaskScreen(viewModel = viewModel)
        }
    }

}