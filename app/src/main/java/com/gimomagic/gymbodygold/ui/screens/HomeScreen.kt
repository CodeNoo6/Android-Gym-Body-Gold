package com.gimomagic.gymbodygold.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.Brain
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import com.gimomagic.gymbodygold.R
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gimomagic.gymbodygold.ui.theme.GymBodyGoldTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    userName: String = "Ruben Camargo",
    onLogout: () -> Unit = {},
    onContactGym: () -> Unit = {}
) {
    var selectedItem by remember { mutableStateOf("Inicio") }


    Scaffold(
        bottomBar = {
            BottomMenuBar(
                selectedItem = selectedItem,
                onItemSelected = { selectedItem = it }
            )
        },
        topBar = {
            TopAppBar(
                title = {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Gym Body Gold",
                            color = BrandGold,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                },
                actions = {
                    IconButton(onClick = onLogout) {
                        Icon(
                            imageVector = Icons.Default.ExitToApp,
                            contentDescription = "Cerrar Sesión",
                            tint = BrandGold
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF19181C)
                )
            )
        },
        containerColor = Color(0xFF19181C)
    ) { paddingValues ->
        when (selectedItem) {
            "Inicio" -> InicioContent(
                userName = userName,
                onContactGym = onContactGym,
                modifier = Modifier.padding(paddingValues)
            )
            "Perfil" -> PerfilContent(
                userName = userName,
                onLogout = onLogout,
                modifier = Modifier.padding(paddingValues)
            )
        }
    }
}

@Composable
fun MembresiaCard(
    plan: String = "Básica",
    estado: String = "Pendiente de Activación",
    precio: String = "$70.000/mes",
    onContactarClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF1E1C1C) // Fondo oscuro
        ),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Encabezado
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Mi Membresía",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color(0xFFFFBF33)
                )
                Text(
                    text = estado,
                    modifier = Modifier
                        .background(
                            color = Color(0xFF8C6B1F),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                    color = Color(0xFFFFBF33),
                    fontSize = 12.sp
                )
            }

            Spacer(modifier = Modifier.height(4.dp))
            Text(text = plan, color = Color.White, fontSize = 14.sp)

            Spacer(modifier = Modifier.height(16.dp))

            // Ícono central
            Icon(
                imageVector = Icons.Default.AccessTime, // ⏰ Cambia por un ícono más cercano
                contentDescription = null,
                modifier = Modifier.size(56.dp),
                tint = Color(0xFFFFBF33)
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Texto principal
            Text(
                text = "Membresía Pendiente de Activación",
                fontWeight = FontWeight.Bold,
                color = Color(0xFFFFBF33),
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Tu membresía $plan está registrada pero aún no ha sido activada por el administrador.",
                color = Color.Gray,
                fontSize = 14.sp,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Precio: $precio",
                fontWeight = FontWeight.Bold,
                color = Color(0xFFFFBF33),
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Contacta al gimnasio para activar tu membresía",
                color = Color.Gray,
                fontSize = 12.sp,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Botón
            Button(
                onClick = onContactarClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFFBF33),
                    contentColor = Color.Black
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Default.Call,
                    contentDescription = "Llamar",
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Contactar Gimnasio", fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun PerfilContent(
    userName: String,
    onLogout: () -> Unit,
    modifier: Modifier = Modifier
) {
    val gold = Color(0xFFFFBF33)

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("👤 Perfil de $userName", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = onLogout,
            colors = ButtonDefaults.buttonColors(containerColor = gold, contentColor = Color.Black)
        ) {
            Text("Cerrar Sesión")
        }
    }
}

@Composable
fun InicioContent(
    userName: String,
    onContactGym: () -> Unit,
    modifier: Modifier = Modifier
) {
    val gold = Color(0xFFFFBF33)
    val darkBg = Color(0xFF19181C)
    val cardBg = Color(0xFF2C2B30)

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        // Bienvenida
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = gold.copy(alpha = 0.9f)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Texto a la izquierda
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = "¡Bienvenido!",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = Color.Black
                    )
                    Text(
                        text = userName,
                        fontSize = 16.sp,
                        color = Color.Black
                    )
                }

                // Logo circular más grande a la derecha
                Box(
                    modifier = Modifier
                        .size(70.dp) // más grande
                        .background(
                            brush = Brush.radialGradient(
                                colors = listOf(
                                    Color(0xFFFFD700),
                                    Color(0xFFFFBF33)
                                )
                            ),
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.logo),
                        contentDescription = "Logo",
                        modifier = Modifier
                            .size(60.dp) // logo más grande dentro del círculo
                            .clip(CircleShape)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Membresía
        MembresiaCard {  }

        Spacer(modifier = Modifier.height(16.dp))

        // Gymius IA
        GymiusCard(
            onClick = {

            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Notificaciones
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = cardBg),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("🔔 Notificaciones", color = Color.White, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(8.dp))
                Text("No hay notificaciones", color = Color.White.copy(0.6f))
            }
        }
    }
}

@Composable
fun ChatInput(
    message: String,
    onMessageChange: (String) -> Unit,
    onSend: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = message,
            onValueChange = onMessageChange,
            placeholder = {
                Text(
                    "Pregunta sobre ejercicios, nutrición...",
                    color = Color.Gray
                )
            },
            shape = RoundedCornerShape(24.dp), // Bordes redondeados
            modifier = Modifier
                .weight(1f)
                .height(50.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = BrandGold,     // borde dorado activo
                unfocusedBorderColor = BrandGold,   // borde dorado inactivo
                cursorColor = BrandGold,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White
            ),
            singleLine = true
        )

        IconButton(
            onClick = { if (message.isNotBlank()) onSend() },
            modifier = Modifier
                .size(50.dp)
                .padding(start = 8.dp)
                .background(BrandGold, CircleShape)
        ) {
            Icon(
                imageVector = Icons.Default.Send,
                contentDescription = "Enviar",
                tint = Color.Black
            )
        }
    }
}

@Composable
fun GymiusCard(onClick: () -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    var message by remember { mutableStateOf("") }

    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1C1C1E)),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column {
            // Cabecera (Gymius IA + botón expandir)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    // Icono circular con cerebro
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .background(Color(0xFFFFC107), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = FontAwesomeIcons.Solid.Brain,
                            contentDescription = "Cerebro",
                            tint = Color.Black,
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Column {
                        Text(
                            text = "Gymius IA",
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            fontSize = 16.sp
                        )
                        Text(
                            text = "En línea",
                            color = Color.Green,
                            fontSize = 12.sp
                        )
                    }
                }

                // Botón de expandir/colapsar
                IconButton(onClick = { expanded = !expanded }) {
                    Icon(
                        imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                        contentDescription = "Expandir chat",
                        tint = Color.White
                    )
                }
            }

            // Chat expandible
            if (expanded) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFF2C2C2E))
                        .padding(12.dp)
                ) {
                    // Mensaje de bienvenida
                    Text(
                        text = "\uD83D\uDC9B ¡Hola! Soy Gymius, tu asistente personal en Gym Body Gold. ¿En qué puedo ayudarte hoy?",
                        color = Color.White,
                        fontSize = 14.sp,
                        modifier = Modifier
                            .background(Color(0xFF1C1C1E), RoundedCornerShape(12.dp))
                            .padding(12.dp)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Caja de texto + botón enviar
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.Transparent),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        ChatInput(
                            message = message,
                            onMessageChange = { message = it },
                            onSend = {
                                // Aquí mandas el mensaje al chat
                                println("Mensaje enviado: $message")
                                message = "" // limpiar campo
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun BottomMenuBar(
    selectedItem: String,
    onItemSelected: (String) -> Unit
) {
    val items = listOf("Inicio", "Perfil")

    NavigationBar(
        containerColor = Color(0xFF19181C)
    ) {
        val items = listOf("Inicio", "Perfil")
        val icons = listOf(Icons.Default.Home, Icons.Default.Person)

        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedItem == item,
                onClick = { onItemSelected(item) },
                icon = {
                    Icon(
                        imageVector = icons[index],
                        contentDescription = item
                    )
                },
                label = {
                    Text(item)
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color(0xFFFFBF33),   // icono dorado activo
                    unselectedIconColor = Color.Gray,       // icono gris inactivo
                    selectedTextColor = Color(0xFFFFBF33),  // texto dorado activo
                    unselectedTextColor = Color.Gray,       // texto gris inactivo
                    indicatorColor = Color.Transparent      // 👈 quita el óvalo
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MembershipScreenPreview() {
    GymBodyGoldTheme {
        HomeScreen()
    }
}
