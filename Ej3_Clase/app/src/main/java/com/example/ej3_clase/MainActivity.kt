package com.example.ej3_clase

import android.os.Bundle
import android.widget.Space
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ej3_clase.ui.theme.Ej3_ClaseTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GeneralPreview()
        }
    }
}

@Composable
fun TransactionItem(title: String, amount: String, date: String){
    Row(
    modifier = Modifier
        .fillMaxWidth()
        .padding(10.dp)
        .clip(RoundedCornerShape(16.dp))
        .background(Color.White.copy(0.05f)), //Opacidad del color blanco
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column() {
            Text(title, color = Color.White)
            Text(date, color = Color.White.copy(alpha = 0.5f), fontSize = 12.sp) //Texto -> sp (dependiente de la densidad de la pantalla)

        }
        Text(
            amount,
            fontWeight = FontWeight.Bold,
            color = if(amount.contains("+")) Color(0xFF17FF08) else Color(0xFF880808))
    }
}

@Composable
fun ActionButton(text: String){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape)
                .background(Color.White.copy(alpha = 0.1f))
                .border(
                    width = 1.dp,
                    Color.White.copy(alpha = 0.2f),
                    CircleShape
                )
                .clickable{},
                contentAlignment = Alignment.Center
        ){
            Text(
                text.first().toString(),
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text, color = Color.White, fontSize = 12.sp
        )
    }

}

@Composable
fun BalanceItem (title: String, amount: String){
    Column() {
        Text(
            title,
            color = Color.White.copy(0.6f),
            fontSize = 12.sp
        )
        Text(
            amount,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
    }

}

@Composable
fun DashboardScreen (
){
    val backgroundGradient = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF9933ff),
            Color(0xFF666ff)
        )
    )
    Box(modifier = Modifier
        .fillMaxWidth()
        .background(brush = backgroundGradient)
    ){
        Column(
            modifier = Modifier.fillMaxSize().padding(20.dp)
        ) {
            Row(){
                Column() {
                    Text(
                        "Welcome Back",
                        color = Color.White.copy(alpha = 0.7f),
                        fontSize = 14.sp
                    )

                    Text(
                        "Andrea",
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                }


            }
        }
    }
}
@Composable
@Preview
fun GeneralPreview(){
TransactionItem("Netflix", "+5.99", "Today")
}

