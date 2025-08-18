package com.gimomagic.gymbodygold.ui.screens.loginScreens.forgotPasswordScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.compose.material3.OutlinedTextField
@Composable
fun ForgotPasswordScreen(
    onBack: () -> Unit = {},
    onVerify: (String) -> Unit = {}
) {
    var email by remember { mutableStateOf(TextFieldValue("")) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(20.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier.fillMaxWidth()
        ) {
            // Botón para cerrar
            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.TopStart
            ) {
                Text(
                    "✕",
                    fontSize = 22.sp,
                    color = Color(0xFFFFBF33),
                    modifier = Modifier.clickable { onBack() }
                )
            }

            Spacer(modifier = Modifier.height(30.dp))

            // Título
            Text(
                text = "Recuperar Contraseña",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFFFBF33)
            )

            Spacer(modifier = Modifier.height(40.dp))

            // Subtítulo
            Text(
                text = "Ingresa tu email",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFFFBF33)
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Verificaremos que tengas una cuenta registrada y te\n" +
                        "enviaremos un código de verificación por email",
                fontSize = 14.sp,
                color = Color(0xFFFFC107),
                lineHeight = 18.sp,
                modifier = Modifier.padding(horizontal = 10.dp),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )

            Spacer(modifier = Modifier.height(40.dp))

            // Campo de texto
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Correo electrónico", color = Color(0xFFFFBF33)) },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Transparent),
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Email, "Icono de email",tint = Color(0xFFBEA314))
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFFFFBF33),
                    unfocusedBorderColor = Color(0xFFFFBF33),
                    cursorColor = Color(0xFFFFBF33),
                    focusedLabelColor = Color(0xFFFFBF33),
                    unfocusedLabelColor = Color(0xFFFFBF33),
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White
                )

            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Warning,
                    contentDescription = "Icono de advertencia",
                    tint = Color(0xFFFFBF33),
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Solo se pueden recuperar cuentas registradas en el sistema",
                    fontSize = 12.sp,
                    color = Color(0xFFFFC107),
                )
            }


            Spacer(modifier = Modifier.height(30.dp))

            // Botón
            Button(
                onClick = { onVerify(email.text) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFBF33))
            ) {
                Text("Verificar y enviar código", color = Color.Black, fontSize = 16.sp)
            }
        }
    }
}
