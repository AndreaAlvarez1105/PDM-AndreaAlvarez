package com.example.firebaseapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.firebaseapp.data.auth.AuthRepository
import com.example.firebaseapp.data.auth.FirebaseAuthRepository
import com.example.firebaseapp.ui.screens.AuthScreen
import com.example.firebaseapp.ui.screens.HomeScreen
import com.example.firebaseapp.ui.theme.FirebaseAppTheme
import com.example.firebaseapp.viewmodel.AuthViewModel
import com.example.firebaseapp.viewmodel.NavigationState

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FirebaseAppTheme {
                FirebaseApp()
            }
        }
    }
}

@Composable
fun FirebaseApp(
    authRepository: AuthRepository = FirebaseAuthRepository(),
    authViewModel: AuthViewModel = viewModel(
        factory = AuthViewModel.Factory(authRepository)
    )
) {
    val navController = rememberNavController()
    val navigationState by authViewModel.navigationState.collectAsState()

    val startDestination = when (navigationState) {
        NavigationState.Authenticated -> "home"
        NavigationState.Unauthenticated -> "auth"
    }

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable("auth") {
            AuthScreen(
                viewModel = authViewModel,
                onAuthSuccess = {
                    authViewModel.clearState()
                    navController.navigate("home") {
                        popUpTo("auth") { inclusive = true }
                    }
                }
            )
        }

        composable("home") {
            if (navigationState == NavigationState.Authenticated) {
                HomeScreen(
                    userEmail = authRepository.getCurrentUser()?.email ?: "",
                    onLogout = {
                        authViewModel.logout()
                        navController.navigate("auth") {
                            popUpTo("home") { inclusive = true }
                        }
                    }
                )
            } else {
                AuthScreen(
                    viewModel = authViewModel,
                    onAuthSuccess = {
                        authViewModel.clearState()
                        navController.navigate("home") {
                            popUpTo("auth") { inclusive = true }
                        }
                    }
                )
            }
        }
    }
}