package com.example.lab03_0073824

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ListaUsuarios(
    modifier: Modifier = Modifier,
    onVolver: () -> Unit
) {

    var listaUsuarios: MutableList<String> = remember { mutableStateListOf<String>() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(25.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly,

        ) {
        val usuario: MutableState<String> = remember { mutableStateOf("") }

        Button(
            onClick = onVolver
        ) {
            Text("Volver")
        }

        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = usuario.value,
            onValueChange = {
                usuario.value = it
            }
        )


        Button(
            modifier = Modifier,
            onClick = {
                if (usuario.value.isNotBlank()) {
                    listaUsuarios.add(usuario.value)
                    usuario.value = ""
                }
            }
        ) {
            Text(text = "Guardar")
        }

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                "Listado de nombres y posición en la lista",
                modifier = Modifier.width(150.dp)
            )

            Button(
                modifier = Modifier,
                onClick = {
                    listaUsuarios.clear()
                }
            ) {
                Text(text = "Limpiar")
            }
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .border(width = 2.dp, color = Color.Blue, shape = RoundedCornerShape(8.dp))
                .weight(0.5f)
        ) {
            itemsIndexed(listaUsuarios) { index, item ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = item
                    )
                    Text(
                        text = (index + 1).toString()
                    )
                }
            }
        }
    }
}