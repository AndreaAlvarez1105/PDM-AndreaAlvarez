package com.example.lab02_pdm

import android.R
import android.R.attr.entries
import android.os.Bundle
import android.renderscript.Allocation
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lab02_pdm.ui.theme.Lab02_PDMTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ListaUsuarios()
        }
    }
}

@Composable
@Preview
fun ListaUsuarios() {

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
                onClick = {listaUsuarios.clear()
                }
            ) {
                Text(text = "Limpiar")
            }
        }

        LazyColumn (modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .border(width = 2.dp, color = Color.Blue, shape = RoundedCornerShape(8.dp))
            .weight(0.5f)) {
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

