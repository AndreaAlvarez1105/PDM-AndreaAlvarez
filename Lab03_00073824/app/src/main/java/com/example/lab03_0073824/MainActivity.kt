package com.example.lab03_0073824

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.lab03_0073824.ui.theme.Lab03_0073824Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Lab03_0073824Theme {
                val navController = rememberNavController()

                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->

                    NavHost(
                        navController = navController,
                        startDestination = "main_graph",
                        modifier = Modifier.padding(innerPadding)
                    ) {

                        navigation(
                            startDestination = "menu",
                            route = "main_graph"
                        ) {

                            composable("menu") {
                                HomeScreen(
                                    onIrLista = {
                                        navController.navigate("lista")
                                    },
                                    onIrSensores = {
                                        navController.navigate("sensores")
                                    }
                                )
                            }

                            composable("lista") {
                                ListaUsuarios(
                                    onVolver = {
                                        navController.popBackStack()
                                    }
                                )
                            }

                            composable("sensores") {
                                Sensores(
                                    onVolver = {
                                        navController.popBackStack()
                                    }
                                )
                            }

                            composable(
                                route = "detail/{itemId}",
                                arguments = listOf(
                                    navArgument("itemId") {
                                        type = NavType.IntType
                                    }
                                )
                            ) { entry ->

                                val itemId = entry.arguments?.getInt("itemId") ?: 0

                                DetailScreen(
                                    itemId = itemId,
                                    onVolver = {
                                        navController.popBackStack()
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DetailScreen(
    itemId: Int,
    onVolver: () -> Unit
) {
    Button(
        onClick = onVolver
    ) {
        Text("Detalle del item: $itemId")
    }
}