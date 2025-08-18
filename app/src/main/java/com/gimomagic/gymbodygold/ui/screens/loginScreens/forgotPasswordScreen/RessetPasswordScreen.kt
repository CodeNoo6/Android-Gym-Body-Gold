package com.gimomagic.gymbodygold.ui.screens.loginScreens.forgotPasswordScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RessetPasswordScreen(
    onBack: () -> Unit,
    onPasswordReset: () -> Unit
) {
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }

    val isLengthValid = password.length >= 6
    val isMatch = password == confirmPassword && password.isNotEmpty()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF121212)) // fondo oscuro
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

            Spacer(modifier = Modifier.height(20.dp))

            // Icono grande
            Box(
                modifier = Modifier
                    .size(90.dp)
                    .background(Color(0xFF1E1E1E), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "Lock",
                    tint = Color(0xFFFFC107),
                    modifier = Modifier.size(48.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Título
            Text(
                text = "Nueva contraseña",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFFFC107)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Ingresa tu nueva contraseña. Debe tener al menos 6 caracteres.",
                fontSize = 14.sp,
                color = Color(0xFFFFC107),
                modifier = Modifier.padding(horizontal = 16.dp),
                lineHeight = 18.sp
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Campo contraseña
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                placeholder = { Text("Nueva contraseña") },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Lock, contentDescription = null, tint = Color(0xFFFFC107))
                },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFFFFC107),
                    unfocusedBorderColor = Color(0xFFFFC107),
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    cursorColor = Color(0xFFFFC107)
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Confirmar contraseña
            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                placeholder = { Text("Confirmar contraseña") },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Lock, contentDescription = null, tint = Color(0xFFFFC107))
                },
                visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFFFFC107),
                    unfocusedBorderColor = Color(0xFFFFC107),
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    cursorColor = Color(0xFFFFC107)
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Validaciones
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = if (isLengthValid) Color(0xFF4CAF50) else Color.Gray,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Al menos 6 caracteres", fontSize = 12.sp, color = if (isLengthValid) Color(0xFF4CAF50) else Color.Gray)
                }
                Spacer(modifier = Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = if (isMatch) Color(0xFF4CAF50) else Color.Gray,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Las contraseñas coinciden", fontSize = 12.sp, color = if (isMatch) Color(0xFF4CAF50) else Color.Gray)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Botón actualizar
            Button(
                onClick = { if (isLengthValid && isMatch) onPasswordReset() },
                enabled = isLengthValid && isMatch,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .padding(horizontal = 8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFFC107),
                    contentColor = Color.Black,
                    disabledContainerColor = Color.Gray
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Actualizar contraseña", fontWeight = FontWeight.Bold)
            }
        }
    }
}
