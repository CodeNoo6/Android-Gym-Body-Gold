package com.gimomagic.gymbodygold.ui.screens.loginScreens.forgotPasswordScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PasswordSuccessScreen(
    onBack: () -> Unit,
    onContinue: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF121212))
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Botón cerrar
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                IconButton(onClick = { onBack() }) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Cerrar",
                        tint = Color(0xFFFFC107)
                    )
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            // Icono de éxito
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .background(Color(0xFF1E1E1E), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Éxito",
                    tint = Color(0xFF4CAF50), // Verde de éxito
                    modifier = Modifier.size(64.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Título
            Text(
                text = "¡Contraseña actualizada!",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFFFC107)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Subtítulo
            Text(
                text = "Tu contraseña ha sido cambiada exitosamente.\nYa puedes iniciar sesión con tu nueva contraseña.",
                fontSize = 14.sp,
                color = Color(0xFFFFC107),
                lineHeight = 18.sp,
                modifier = Modifier.padding(horizontal = 16.dp),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )

            Spacer(modifier = Modifier.height(40.dp))

            // Botón continuar
            Button(
                onClick = { onContinue() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .padding(horizontal = 8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFFC107),
                    contentColor = Color.Black
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Continuar al inicio de sesión", fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Nota inferior
            Text(
                text = "Recuerda guardar tu nueva contraseña en un lugar seguro",
                fontSize = 12.sp,
                color = Color(0xFFFFC107).copy(alpha = 0.7f),
                modifier = Modifier.padding(horizontal = 16.dp),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
        }
    }
}
