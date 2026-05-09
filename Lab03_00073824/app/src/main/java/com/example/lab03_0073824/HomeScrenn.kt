package com.example.lab03_0073824

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onIrLista: () -> Unit,
    onIrSensores: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Laboratorio 3 - PDM",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Andrea Álvarez - 00073824",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = onIrLista,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Ir a lista de nombres")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onIrSensores,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Sensores")
        }
    }
}