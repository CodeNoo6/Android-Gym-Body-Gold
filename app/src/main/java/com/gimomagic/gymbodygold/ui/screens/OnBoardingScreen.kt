package com.gimomagic.gymbodygold.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun OnBoardingScreen(
    onSkip: () -> Unit,
    onNext: () -> Unit
) {
    var currentPage by remember { mutableStateOf(0) }
    var showClases by remember { mutableStateOf(false) }

    val pages = listOf(
        OnboardingPage(
            title = "Bienvenido a Gym\nBody Gold",
            subtitle = "Entrena como nunca antes",
            description = "Clases exclusivas y todo lo que\nnecesitas para alcanzar tus objetivos."
        ),
        OnboardingPage(
            title = "Consejos Inteligentes",
            subtitle = "Tu entrenador virtual",
            description = "Recibe recomendaciones personalizadas con IA\npara mejorar tu entrenamiento y nutrición."
        ),
        OnboardingPage(
            title = "Clases Grupales y Más",
            subtitle = "Actívate con buena energía",
            description = "Zumba, funcional, spinning, y\nmuchas más."
        ),
        OnboardingPage(
            title = "Entrenamiento\nPersonalizado",
            subtitle = "Rutinas diseñadas para ti",
            description = "Mejora con el acompañamiento\nde tu coach."
        ),
        OnboardingPage(
            title = "Planes a tu medida",
            subtitle = "Tú decides cómo y cuándo",
            description = "Elige entre planes mensuales,\nsemanales o por clase."
        )
    )

    if (showClases) {
        ClasesGrupalesScreen(
            onBack = {
                showClases = false
                currentPage = pages.lastIndex
            }
        )
    } else {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .padding(24.dp)
        ) {
            val page = pages[currentPage]

            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // Botón Saltar en la esquina superior derecha
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(
                        onClick = onSkip,
                        modifier = Modifier
                            .border(
                                width = 1.dp,
                                color = Color(0xFFBEA314), // Borde amarillo
                                shape = RoundedCornerShape(50.dp)
                            )
                            .background(Color(0xFF292929), RoundedCornerShape(50.dp)) // Fondo gris
                            .padding(horizontal = 20.dp)
                    ) {
                        Text(
                            text = "Saltar",
                            color = Color(0xFFBEA314), // Texto amarillo
                            fontWeight = FontWeight.Bold
                        )
                    }
                }


                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = page.title,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = page.subtitle,
                        color = Color(0xFFFFBF33),
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = page.description,
                        color = Color.LightGray,
                        textAlign = TextAlign.Center
                    )
                }

                // Indicadores
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    repeat(pages.size) { index ->
                        Box(
                            modifier = Modifier
                                .size(if (index == currentPage) 12.dp else 8.dp)
                                .background(
                                    if (index == currentPage) Color(0xFFFFBF33) else Color.Gray,
                                    CircleShape
                                )
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                }

                // Botones Anterior / Siguiente
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    OutlinedButton(
                        onClick = {
                            if (currentPage > 0) currentPage--
                        },
                        border = BorderStroke(1.dp, Color(0xFFBEA314)),
                        shape = RoundedCornerShape(50),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = Color.Transparent,
                            contentColor = Color(0xFFBEA314)
                        ),
                        modifier = Modifier.height(50.dp)
                    ) {
                        Text("Anterior")
                    }

                    // Definimos variables para mejor legibilidad
                    val isLastPage = currentPage == pages.lastIndex
                    val buttonText = if (isLastPage) "Comenzar ->" else "Siguiente"

                    Button(
                        onClick = {
                            if (!isLastPage) {
                                currentPage++
                                onNext()
                            } else {
                                showClases = true
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFBEA314),
                            contentColor = Color.Black
                        ),
                        shape = RoundedCornerShape(50),
                        modifier = Modifier.height(50.dp)
                    ) {
                        Text(buttonText)
                    }
                }
            }
        }
    }
}

@Composable
fun ClasesGrupalesScreen(onBack: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(modifier = Modifier.height(32.dp))
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Clases Grupales y Más",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Actívate con buena energía",
                    color = Color(0xFFFFBF33),
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Zumba, funcional, spinning, y muchas más.",
                    color = Color.LightGray,
                    textAlign = TextAlign.Center
                )
            }

            // Botón Atrás en footer
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                OutlinedButton(
                    onClick = onBack,
                    border = BorderStroke(1.dp, Color(0xFFFFBF33)),
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = Color.Transparent,
                        contentColor = Color(0xFFFFBF33)
                    ),
                    modifier = Modifier.height(50.dp)
                ) {
                    Text("Anterior")
                }
            }
        }
    }
}

data class OnboardingPage(
    val title: String,
    val subtitle: String,
    val description: String
)
