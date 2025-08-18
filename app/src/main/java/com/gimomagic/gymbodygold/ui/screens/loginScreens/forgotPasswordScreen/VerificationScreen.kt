package com.gimomagic.gymbodygold.ui.screens.loginScreens.forgotPasswordScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun VerificationScreen(
    email: String,
    onBack: () -> Unit = {},
    onVerify: (String) -> Unit = {},
    onResend: () -> Unit = {}
) {
    var code by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF000000))
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Botón de cerrar
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.TopStart
        ) {
            Text(
                "✕",
                fontSize = 22.sp,
                color = Color(0xFFFFBF33),
                modifier = Modifier.clickable { onBack() }
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Título
        Text(
            text = "Recuperar Contraseña",
            color = Color(0xFFFFC107),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(40.dp))

        // Subtítulo
        Text(
            text = "Código enviado",
            color = Color(0xFFFFC107),
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Mensaje
        Text(
            text = "Hemos enviado un código de 6 dígitos a:",
            color = Color(0xFFFFC107),
            fontSize = 14.sp,
            textAlign = TextAlign.Center
        )

        Text(
            text = email,
            color = Color(0xFFFFC107),
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Expiración
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.Warning,
                contentDescription = "Icono de advertencia",
                tint = Color(0xFFFFBF33),
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "El código expira en 10 minutos",
                fontSize = 12.sp,
                color = Color(0xFFFFC107),
            )
        }

        Spacer(modifier = Modifier.height(30.dp))

        // Input de código con recuadros
        BasicTextField(
            value = code,
            onValueChange = { if (it.length <= 6) code = it },
            decorationBox = {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    repeat(6) { index ->
                        Box(
                            modifier = Modifier
                                .size(45.dp)
                                .background(Color.Transparent, RoundedCornerShape(8.dp))
                                .border(1.dp, Color(0xFFFFC107), RoundedCornerShape(8.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = code.getOrNull(index)?.toString() ?: "",
                                color = Color.White,
                                fontSize = 18.sp
                            )
                        }
                    }
                }
            },
            textStyle = TextStyle(color = Color.White, fontSize = 18.sp)
        )

        Spacer(modifier = Modifier.height(30.dp))

        // Botón verificar
        Button(
            onClick = { onVerify(code) },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFFFC107),
                contentColor = Color.Black
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Verificar código")
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Reenviar
        Text(
            text = "¿No recibiste el código? Reenviar",
            color = Color(0xFFFFC107),
            fontSize = 14.sp,
            modifier = Modifier.clickable { onResend() }
        )
    }
}
