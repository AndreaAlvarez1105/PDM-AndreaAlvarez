package com.example.ej14_pdm

import android.R
import android.R.attr.text
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.magnifier
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.remote.creation.first
import androidx.compose.runtime.Composable
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ej14_pdm.ui.theme.Ej14_PDMTheme
import org.intellij.lang.annotations.JdkConstants
import kotlin.text.first

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Ej14_PDMTheme {

            }
        }
    }
}

@Composable
fun Pantalla01() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF72EAFF),
                        Color(0xFF72C2FF),
                        Color(0xFF003888)
                    )
                )
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            "San Miguel",
            fontWeight = FontWeight.Bold,
            color = Color.White,
            fontSize = 35.sp,

            )


        Text(
            "46°C",
            fontSize = 65.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White

        )

        Spacer(modifier = Modifier.height(25.dp))

        Text(
            "🧭",
            fontSize = 85.sp,
        )

        Spacer(modifier = Modifier.height(15.dp))

        Card(
            colors = CardDefaults.cardColors(
                containerColor = Color.White.copy(alpha = 0.2f),
            ),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.padding(15.dp)

        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "HUM",
                        color = Color.White.copy(alpha = 0.4f)

                    )

                    Spacer(modifier = Modifier.height(5.dp))

                    Text(
                        "65%",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White

                    )
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally

                ) {
                    Text(
                        "VIENTO",
                        color = Color.White.copy(alpha = 0.4f)

                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        "12 km/h",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White

                    )
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally

                ) {
                    Text(
                        "LLUVIA",
                        color = Color.White.copy(alpha = 0.4f)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        "10%",
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        color = Color.White

                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(25.dp))

        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .background(color = Color.White),
            contentAlignment = Alignment.Center
        ) {
            Text(
                "ACTUALIZAR",
                fontWeight = FontWeight.Bold,
                color = Color.Blue,
                modifier = Modifier.padding(15.dp)


            )
        }


    }
}


@Composable
@Preview
fun Pantalla02() {


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFA0B5EB),
                        Color(0xFFEA52F8),
                        Color(0xFF0066FF)

                    )

                )

            )
            .padding(28.dp),
        horizontalAlignment = AbsoluteAlignment.Left,
        verticalArrangement = Arrangement.Center
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "El Salvador",
                fontWeight = FontWeight.Bold,
                color = Color.White,
                fontSize = 30.sp,

                )


            Text(
                "25°C", fontSize = 55.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White

            )


            Text(
                "Soleado", fontSize = 18.sp,
                color = Color.White

            )

        }
        Spacer(modifier = Modifier.height(25.dp))

        Column() {
            Text(
                "PRONÓSTICO POR HORAS",
                fontSize = 14.sp,
                color = Color.White.copy(alpha = 0.7f)

            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                PronosticoPorHora("Ahora", "☀️", "22°C")
                PronosticoPorHora("14:00", "⛅", "22°C")
                PronosticoPorHora("16:00", "🌥️️", "22°C")
                PronosticoPorHora("18:00", "🌦️", "22°C")
                PronosticoPorHora("20:00", "⛈️", "22°C")
            }

            Spacer(modifier = Modifier.height(25.dp))

        }

        Card(
            colors = CardDefaults.cardColors(
                containerColor = Color.White.copy(alpha = 0.2f),
            ),
            shape = RoundedCornerShape(16.dp),

            ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp),
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    "DETALLES",
                    fontSize = 14.sp,
                    color = Color.White.copy(alpha = 0.7f)

                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Detalles("Humedad", "65%")
                    Detalles("Viento", "12km/h")
                }

                Spacer(modifier = Modifier.height(5.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Detalles("Presión", "1012hp")
                    Detalles("UV", "5")
                }

            }
        }

        Spacer(modifier = Modifier.height(25.dp))

        Text(
            "PRONÓSTICO SEMANAL",
            fontSize = 14.sp,
            color = Color.White.copy(alpha = 0.7f)

        )

        Spacer(modifier = Modifier.height(10.dp))

        Card(
            colors = CardDefaults.cardColors(
                containerColor = Color.White.copy(alpha = 0.2f),
            ),
            shape = RoundedCornerShape(16.dp),

            ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                PronosticoSemanal("Lunes", "☀️", "20", " /20°")
                PronosticoSemanal("14:00", "⛅", "22", " /20°")
                PronosticoSemanal("16:00", "🌥️️", "22°", " /20°")
                PronosticoSemanal("18:00", "🌦️", "22°", " /20°")
                PronosticoSemanal("20:00", "⛈️", "22°", " /20°")
            }

        }
    }

}


@Composable
fun PronosticoPorHora(tiempo: String, emoji: String, temperatura: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center

    ) {
        Text(
            tiempo, color = Color.White,
            fontSize = 14.sp

        )


        Text(
            emoji, fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White

        )


        Text(
            temperatura, fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White

        )
    }
}

@Composable
fun Detalles(titulo: String, detalle: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            titulo, fontSize = 13.sp,
            color = Color.White.copy(alpha = 0.7f)
        )

        Spacer(modifier = Modifier.height(5.dp))

        Text(
            detalle, fontSize = 17.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White

        )
    }
}

@Composable
fun PronosticoSemanal(dia: String, emoji: String, temperatura: String, temp: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            dia, color = Color.White,
            fontSize = 15.sp

        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            emoji, fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White

        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                temperatura, fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White

            )

            Text(
                temp, fontSize = 15.sp,
                color = Color.White.copy(alpha = 0.3f)

            )
        }
    }
    Spacer(modifier = Modifier.height(6.dp))
    HorizontalDivider(
        color = Color.White.copy(alpha = 0.4f),
        thickness = 1.dp
    )
    Spacer(modifier = Modifier.height(6.dp))


}