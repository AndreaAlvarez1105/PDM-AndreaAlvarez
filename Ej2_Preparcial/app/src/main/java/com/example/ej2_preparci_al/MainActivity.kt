package com.example.ej2_preparci_al

import android.R.attr.onClick
import android.os.Bundle
import android.service.autofill.OnClickAction
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ej2_preparci_al.ui.theme.Ej2_PreparcialTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Ej2_PreparcialTheme {
                PreviewScreen()
            }
        }
    }
}

// --- COLORES ---
val PurpleHeader = Color(0xFF4A148C)
val White = Color(0xFFF8F9FE)

@Composable
@Preview
fun PreviewScreen() {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(White) // Fondo base de toda la pantalla
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(280.dp)
                .background(PurpleHeader)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp, vertical = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(40.dp))

            // Foto DE PERFIL Y campanita de notificación
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.img),
                    contentDescription = null,
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                        .border(1.dp, Color.White, CircleShape),
                    contentScale = ContentScale.Crop
                )

                IconButton(
                    onClick = { },
                    modifier = Modifier
                        .background(Color.White.copy(alpha = 0.1f), RoundedCornerShape(12.dp))
                ) {
                    Icon(Icons.Default.Notifications, "Notificación", tint = Color.White)
                }
            }

            Spacer(modifier = Modifier.height(25.dp))

            Text("Available Balance", color = Color.White.copy(alpha = 0.7f), fontSize = 14.sp)
            Text("$450.54", color = Color.White, fontSize = 36.sp, fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.height(30.dp))

            // TARJETA FLOTANTE DE ACCIONES
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(6.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 20.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    QuickAction("💳", "Top Up")
                    QuickAction("💳", "Send")
                    QuickAction("💳", "Request")
                    QuickAction("💳", "History")
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

            Text(
                "Payment List",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(16.dp))

            //Iconos de servicios con sombreado
            Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween) {
                ServiceIcon("🛜", "Internet", Color(0xFFE57373))
                ServiceIcon("⚡", "Electricity", Color(0xFFFFB74D))
                ServiceIcon("💱", "Voucher", Color(0xFF81C784))
                ServiceIcon("🤝", "Assurance", Color(0xFF4FC3F7))
            }

            Spacer(modifier = Modifier.height(20.dp))

            Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween) {
                ServiceIcon("💳", "M Card", Color(0xFF64B5F6))
                ServiceIcon("📝", "Bill", Color(0xFF9575CD))
                ServiceIcon("🛒", "Merchant", Color(0xFFF06292))
                ServiceIcon("➕", "More", Color(0xFF9575CD))
            }

            Spacer(modifier = Modifier.height(30.dp))

            Text(
                "Promo & Discount",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {

                Card(
                    modifier = Modifier.width(150.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(6.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.img),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth(),
                        contentScale = ContentScale.Crop
                    )
                }
                Card(
                    modifier = Modifier.width(150.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(6.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.img),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth(),
                        contentScale = ContentScale.Crop
                    )
                }
            }

        }

        FloatingActionButton(
            onClick = { },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 30.dp)
                .size(65.dp),
            containerColor = PurpleHeader,
            contentColor = Color.White,
            shape = CircleShape
        ) {
            Icon(
                imageVector = Icons.Default.Call,
                contentDescription = "Agregar nuevo",
                modifier = Modifier.size(35.dp)
            )
        }

    }
}

@Composable
fun QuickAction(icon: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(icon, fontSize = 28.sp)
        Spacer(Modifier.height(4.dp))
        Text(label, fontSize = 12.sp, fontWeight = FontWeight.Medium)
    }
}

@Composable
fun ServiceIcon(icon: String, label: String, color: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.width(75.dp)) {
        Box(
            modifier = Modifier
                .size(60.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(color.copy(alpha = 0.1f)),
            contentAlignment = Alignment.Center
        ) {
            Text(icon, fontSize = 28.sp)
        }
        Spacer(Modifier.height(8.dp))
        Text(label, fontSize = 11.sp, color = Color.Gray)
    }
}






