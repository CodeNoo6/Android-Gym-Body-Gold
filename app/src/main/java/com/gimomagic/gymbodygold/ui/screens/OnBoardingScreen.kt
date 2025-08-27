package com.gimomagic.gymbodygold.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import com.gimomagic.gymbodygold.R
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlin.math.cos
import kotlin.math.sin
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.EmojiEvents

import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlin.math.cos
import kotlin.math.sin
import androidx.compose.ui.text.TextStyle

val BrandGold = Color(0xFFFFBF33)
val BrandDark = Color(0xFF262626)
val BrandAccent = Color(0xFFFFA619)
val BrandLight = Color(0xFFFFD966)
val BrandBlack = Color(0xFF0D0D0D)
val BrandGray = Color(0xFF404040)
val BrandWhite = Color(0xFFFFFFFF)
@Composable
fun OnBoardingScreen(
    onSkip: () -> Unit,
    onNext: () -> Unit,
    onComplete: () -> Unit = {}
) {
    var currentPage by remember { mutableStateOf(0) }

    val pages = listOf(
        OnboardingPage(
            title = "Bienvenido a Gym\nBody Gold",
            subtitle = "Entrena como nunca antes",
            description = "Clases exclusivas y todo lo que necesitas para alcanzar tus objetivos.",
            isSpecial = true
        ),
        OnboardingPage(
            title = "Conoce a Gymius",
            subtitle = "Tu asistente personal con IA",
            description = "",
            isAI = true
        ),
        OnboardingPage(
            title = "Clases Grupales y Más",
            subtitle = "Actívate con buena energía",
            description = "Zumba, funcional, spinning, y muchas más actividades grupales."
        ),
        OnboardingPage(
            title = "Planes a Tu Medida",
            subtitle = "Tú decides cómo y cuándo",
            description = "Elige entre planes mensuales, semanales o por clase."
        ),
        OnboardingPage(
            title = "Entrenamiento\nPersonalizado",
            subtitle = "Rutinas diseñadas para ti",
            description = "Mejora con el acompañamiento de tu coach y la guía de Gymius."
        )
    )


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(24.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Botón Saltar
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                OutlinedButton(
                    onClick = onSkip,
                    border = BorderStroke(1.dp, BrandGold.copy(alpha = 0.5f)),
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = BrandGray.copy(alpha = 0.3f),
                        contentColor = BrandGold
                    )
                ) {
                    Text("Saltar")
                }
            }

            // Contenido de la página
            OnboardingPageContent(
                page = pages[currentPage],
                modifier = Modifier.weight(1f)
            )

            // Controles inferiores
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(30.dp)
            ) {
                // Indicadores de página
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = Modifier.weight(1f))
                    repeat(pages.size) { index ->
                        PageIndicator(
                            isActive = index == currentPage,
                            onClick = { currentPage = index }
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                }

                // Botones de navegación
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Botón Anterior
                    if (currentPage > 0) {
                        OutlinedButton(
                            onClick = { if (currentPage > 0) currentPage-- },
                            border = BorderStroke(1.dp, BrandGold.copy(alpha = 0.5f)),
                            shape = RoundedCornerShape(25.dp),
                            colors = ButtonDefaults.outlinedButtonColors(
                                containerColor = BrandGray.copy(alpha = 0.2f),
                                contentColor = BrandGold
                            ),
                            modifier = Modifier.height(50.dp)
                        ) {
                            Text("Anterior")
                        }
                    } else {
                        Spacer(modifier = Modifier.width(1.dp))
                    }

                    // Botón Siguiente/Comenzar
                    val isLastPage = currentPage == pages.lastIndex
                    val buttonText = if (isLastPage) "¡Comenzar!" else "Siguiente"

                    Button(
                        onClick = {
                            when {
                                !isLastPage -> {
                                    currentPage++
                                    onNext()
                                }
                                else -> onComplete()
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = BrandGold,
                            contentColor = BrandBlack
                        ),
                        shape = RoundedCornerShape(25.dp),
                        modifier = Modifier
                            .height(50.dp)
                            .scale(if (isLastPage) 1.05f else 1.0f)
                    ) {
                        Text(
                            text = buttonText,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun OnboardingPageContent(
    page: OnboardingPage,
    modifier: Modifier = Modifier
) {
    var isAnimating by remember { mutableStateOf(false) }
    var showFeatures by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        isAnimating = true
        delay(500)
        showFeatures = true
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.height(20.dp))

        // Icono con efectos especiales
        Box(
            modifier = Modifier
                .height(160.dp)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            when {
                page.isSpecial -> WelcomeLogoAnimation(isAnimating = isAnimating)
                page.isAI -> AIGymiusAnimation(isAnimating = isAnimating)
                page.title.contains("Clases") -> GroupClassesAnimation(isAnimating = isAnimating)
                page.title.contains("Planes") -> PlansAnimation(isAnimating = isAnimating)
                page.title.contains("Entrenamiento") -> PersonalTrainingAnimation(isAnimating = isAnimating)
            }
        }

        Spacer(modifier = Modifier.height(30.dp))

        // Contenido del texto
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Text(
                text = page.title,
                color = BrandWhite,
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp,
                textAlign = TextAlign.Center,
                lineHeight = 32.sp
            )

            if (page.isAI) {
                Text(
                    text = page.subtitle,
                    brush = Brush.linearGradient(
                        colors = listOf(BrandAccent, BrandGold, BrandLight)
                    ),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center
                )
            } else {
                Text(
                    text = page.subtitle,
                    color = BrandGold,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center
                )
            }

            if (page.description.isNotEmpty()) {
                Text(
                    text = page.description,
                    color = BrandLight.copy(alpha = 0.9f),
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(horizontal = 30.dp),
                    lineHeight = 22.sp
                )
            }

            // Features específicas por página
            if (showFeatures) {
                FeaturesSection(page = page)
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
fun FeaturesSection(page: OnboardingPage) {
    val features = getPageFeatures(page)

    if (features.isNotEmpty()) {
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.padding(horizontal = 20.dp)
        ) {
            features.forEachIndexed { index, feature ->
                var isVisible by remember { mutableStateOf(false) }

                LaunchedEffect(Unit) {
                    delay((index * 150).toLong())
                    isVisible = true
                }

                FeatureRow(
                    icon = feature.first, // Usa ImageVector directamente
                    text = feature.second,
                    isVisible = isVisible
                )
            }
        }
    }
}

@Composable
fun FeatureRow(
    icon: ImageVector,
    text: String,
    isVisible: Boolean
) {
    val offsetX by animateFloatAsState(
        targetValue = if (isVisible) 0f else -30f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "featureOffset"
    )

    val alpha by animateFloatAsState(
        targetValue = if (isVisible) 1f else 0f,
        animationSpec = tween(600),
        label = "featureAlpha"
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .graphicsLayer {
                translationX = offsetX
                this.alpha = alpha
            }
            .padding(horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = BrandGold,
            modifier = Modifier.size(16.dp)
        )

        Text(
            text = text,
            color = BrandLight.copy(alpha = 0.9f),
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun PageIndicator(
    isActive: Boolean,
    onClick: () -> Unit
) {
    val width by animateDpAsState(
        targetValue = if (isActive) 24.dp else 8.dp,
        animationSpec = spring(),
        label = "indicatorWidth"
    )

    Box(
        modifier = Modifier
            .size(width = width, height = 8.dp)
            .background(
                color = if (isActive) BrandGold else BrandGray.copy(alpha = 0.5f),
                shape = RoundedCornerShape(4.dp)
            )
            .clickable { onClick() }
    )
}

// Animaciones especiales
@Composable
fun WelcomeLogoAnimation(isAnimating: Boolean) {
    var rotation by remember { mutableStateOf(0f) }
    val scale by animateFloatAsState(
        targetValue = if (isAnimating) 1.05f else 1.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(2500),
            repeatMode = RepeatMode.Reverse
        ),
        label = "logoScale"
    )

    LaunchedEffect(isAnimating) {
        while (isAnimating) {
            rotation += 360f
            delay(10000)
        }
    }

    Box(
        modifier = Modifier.size(120.dp),
        contentAlignment = Alignment.Center
    ) {
        // Partículas flotantes
        repeat(8) { index ->
            val angle = (index * 45).toFloat()
            val particleOffset by animateFloatAsState(
                targetValue = if (isAnimating) 60f else 45f,
                animationSpec = infiniteRepeatable(
                    animation = tween(3000),
                    repeatMode = RepeatMode.Reverse
                ),
                label = "particle$index"
            )

            Box(
                modifier = Modifier
                    .size(4.dp)
                    .offset(
                        x = (cos(Math.toRadians(angle.toDouble())) * particleOffset).dp,
                        y = (sin(Math.toRadians(angle.toDouble())) * particleOffset).dp
                    )
                    .background(BrandGold.copy(alpha = 0.6f), CircleShape)
            )
        }

        Image(
            painter = painterResource(id = R.drawable.logo), // Tu logo
            contentDescription = "Gym Body Gold Logo",
            modifier = Modifier
                .size(80.dp)
                .scale(scale)
        )
    }
}

@Composable
fun AIGymiusAnimation(isAnimating: Boolean) {
    var neuralFlow by remember { mutableStateOf(0f) }
    val pulseScale by animateFloatAsState(
        targetValue = if (isAnimating) 1.15f else 1.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000),
            repeatMode = RepeatMode.Reverse
        ),
        label = "brainPulse"
    )

    LaunchedEffect(isAnimating) {
        while (isAnimating) {
            neuralFlow += 360f
            delay(4000)
        }
    }

    Box(
        modifier = Modifier.size(120.dp),
        contentAlignment = Alignment.Center
    ) {
        // Red neuronal
        repeat(12) { index ->
            val angle = (index * 30).toFloat()
            Box(
                modifier = Modifier
                    .size(2.dp)
                    .offset(
                        x = (cos(Math.toRadians(angle.toDouble())) * 50).dp,
                        y = (sin(Math.toRadians(angle.toDouble())) * 50).dp
                    )
                    .background(BrandAccent.copy(alpha = 0.7f), CircleShape)
            )
        }

        Box(
            modifier = Modifier
                .size(80.dp)
                .scale(pulseScale)
                .background(
                    Brush.linearGradient(
                        colors = listOf(BrandAccent, BrandGold)
                    ),
                    CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Filled.Psychology,
                contentDescription = "Gymius IA",
                tint = BrandBlack,
                modifier = Modifier.size(45.dp)
            )
        }
    }
}

@Composable
fun GroupClassesAnimation(isAnimating: Boolean) {
    var danceRotation by remember { mutableStateOf(0f) }
    val energyPulse by animateFloatAsState(
        targetValue = if (isAnimating) 1.2f else 1.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500),
            repeatMode = RepeatMode.Reverse
        ),
        label = "energyPulse"
    )

    LaunchedEffect(isAnimating) {
        while (isAnimating) {
            danceRotation += 360f
            delay(8000)
        }
    }

    Box(
        modifier = Modifier.size(120.dp),
        contentAlignment = Alignment.Center
    ) {
        repeat(6) { index ->
            val angle = (index * 60 + danceRotation).toFloat()
            Box(
                modifier = Modifier
                    .size(20.dp)
                    .scale(energyPulse)
                    .offset(
                        x = (cos(Math.toRadians(angle.toDouble())) * 40).dp,
                        y = (sin(Math.toRadians(angle.toDouble())) * 40).dp
                    )
                    .background(
                        Brush.radialGradient(
                            colors = listOf(BrandLight.copy(alpha = 0.6f), Color.Transparent)
                        ),
                        CircleShape
                    )
            )
        }

        Box(
            modifier = Modifier
                .size(70.dp)
                .background(
                    Brush.linearGradient(
                        colors = listOf(BrandLight, BrandWhite)
                    ),
                    CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Filled.Groups,
                contentDescription = "Clases Grupales",
                tint = BrandBlack,
                modifier = Modifier.size(35.dp)
            )
        }
    }
}

@Composable
fun PlansAnimation(isAnimating: Boolean) {
    var ringRotation by remember { mutableStateOf(0f) }
    val priceGlow by animateFloatAsState(
        targetValue = if (isAnimating) 0.8f else 0.3f,
        animationSpec = infiniteRepeatable(
            animation = tween(2500),
            repeatMode = RepeatMode.Reverse
        ),
        label = "priceGlow"
    )

    LaunchedEffect(isAnimating) {
        while (isAnimating) {
            ringRotation += 360f
            delay(15000)
        }
    }

    Box(
        modifier = Modifier.size(120.dp),
        contentAlignment = Alignment.Center
    ) {
        // Anillos de valor
        repeat(3) { index ->
            Box(
                modifier = Modifier
                    .size((80 + index * 15).dp)
                    .rotate(ringRotation + (index * 45))
                    .border(
                        width = 2.dp,
                        brush = Brush.linearGradient(
                            colors = listOf(BrandGold.copy(alpha = 0.6f), Color.Transparent)
                        ),
                        shape = RoundedCornerShape(15.dp)
                    )
            )
        }

        // Icono principal
        Box(
            modifier = Modifier
                .size(60.dp)
                .background(
                    Brush.linearGradient(
                        colors = listOf(BrandGold, BrandAccent)
                    ),
                    RoundedCornerShape(10.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "💳",
                fontSize = 30.sp
            )
        }
    }
}

@Composable
fun PersonalTrainingAnimation(isAnimating: Boolean) {
    var targetRotation by remember { mutableStateOf(0f) }
    val progressGlow by animateFloatAsState(
        targetValue = if (isAnimating) 0.9f else 0.4f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000),
            repeatMode = RepeatMode.Reverse
        ),
        label = "progressGlow"
    )

    LaunchedEffect(isAnimating) {
        while (isAnimating) {
            targetRotation += 360f
            delay(10000)
        }
    }

    Box(
        modifier = Modifier.size(120.dp),
        contentAlignment = Alignment.Center
    ) {
        // Círculo de progreso
        Canvas(modifier = Modifier.size(100.dp)) {
            drawArc(
                brush = Brush.linearGradient(
                    colors = listOf(BrandAccent, BrandGold)
                ),
                startAngle = 0f,
                sweepAngle = 270f,
                useCenter = false,
                style = androidx.compose.ui.graphics.drawscope.Stroke(8.dp.toPx()),
                alpha = progressGlow
            )
        }

        // Icono central
        Box(
            modifier = Modifier
                .size(60.dp)
                .background(
                    Brush.linearGradient(
                        colors = listOf(BrandAccent, BrandGold)
                    ),
                    CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "🎯",
                fontSize = 30.sp
            )
        }
    }
}

// Pantalla de introducción a Gymius
@Composable
fun GymiusIntroScreen(onDismiss: () -> Unit) {
    var showFeatures by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(800)
        showFeatures = true
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.linearGradient(
                    colors = listOf(BrandDark, BrandAccent.copy(alpha = 0.2f), BrandDark)
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(30.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Conoce a",
                        color = BrandLight,
                        fontSize = 18.sp
                    )
                    Text(
                        text = "Gymius",
                        brush = Brush.linearGradient(
                            colors = listOf(BrandAccent, BrandGold, BrandLight)
                        ),
                        fontSize = 36.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                IconButton(onClick = onDismiss) {
                    Text("✕", color = BrandLight.copy(alpha = 0.7f), fontSize = 22.sp)
                }
            }

            // Contenido principal
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(25.dp)
            ) {
                AIGymiusAnimation(isAnimating = true)

                Text(
                    text = "Tu Asistente Personal con IA",
                    color = BrandGold,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center
                )

                Text(
                    text = "Gymius es la primera IA especializada en fitness que te acompañará en cada paso de tu transformación.",
                    color = BrandLight.copy(alpha = 0.9f),
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    lineHeight = 22.sp
                )

                // Features de Gymius
                if (showFeatures) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(15.dp)
                    ) {
                        val gymiusFeatures = listOf(
                            "🧠" to "Rutinas inteligentes",
                            "🎧" to "Soporte 24/7 para tus dudas",
                            "💪" to "Motivación personalizada"
                        )

                        gymiusFeatures.forEachIndexed { index, (icon, text) ->
                            var isVisible by remember { mutableStateOf(false) }

                            LaunchedEffect(Unit) {
                                delay((index * 200).toLong())
                                isVisible = true
                            }

                            GymiusFeatureRow(
                                icon = icon,
                                text = text,
                                isVisible = isVisible
                            )
                        }
                    }
                }
            }

            // CTA
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                Text(
                    text = "¿Listo para entrenar con Gymius?",
                    color = BrandWhite,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center
                )

                Button(
                    onClick = onDismiss,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = BrandGold,
                        contentColor = BrandBlack
                    ),
                    shape = RoundedCornerShape(25.dp),
                    modifier = Modifier
                        .height(50.dp)
                        .scale(if (showFeatures) 1.0f else 0.8f)
                ) {
                    Text(
                        text = "¡Comenzar con Gymius!",
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}

@Composable
fun GymiusFeatureRow(
    icon: String,
    text: String,
    isVisible: Boolean
) {
    val offsetX by animateFloatAsState(
        targetValue = if (isVisible) 0f else -50f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "gymiusFeatureOffset"
    )

    val alpha by animateFloatAsState(
        targetValue = if (isVisible) 1f else 0f,
        animationSpec = tween(600),
        label = "gymiusFeatureAlpha"
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .graphicsLayer {
                translationX = offsetX
                this.alpha = alpha
            }
            .background(
                BrandGray.copy(alpha = 0.1f),
                RoundedCornerShape(16.dp)
            )
            .border(
                1.dp,
                BrandGold.copy(alpha = 0.3f),
                RoundedCornerShape(16.dp)
            )
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = icon,
            fontSize = 24.sp
        )

        Text(
            text = text,
            color = BrandLight.copy(alpha = 0.9f),
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.weight(1f)
        )
    }
}

fun getPageFeatures(page: OnboardingPage): List<Pair<ImageVector, String>> {
    return when {
        page.title.contains("Bienvenido") -> listOf(
            Icons.Filled.Star to "Equipos de última generación",
            Icons.Filled.Favorite to "Ambiente motivador",
            Icons.Filled.EmojiEvents to "Alcanza tus metas"
        )
        page.title.contains("Gymius") -> listOf(
            Icons.Filled.Settings to "Rutinas inteligentes",
            Icons.Filled.Support to "Soporte 24/7 para tus dudas",
            Icons.Filled.TrendingUp to "Motivación personalizada"
        )
        page.title.contains("Clases") -> listOf(
            Icons.Filled.MusicNote to "Zumba y bailes",
            Icons.Filled.Whatshot to "Entrenamiento funcional",
            Icons.Filled.DirectionsBike to "Spinning energético"
        )
        page.title.contains("Planes") -> listOf(
            Icons.Filled.DateRange to "Planes mensuales",
            Icons.Filled.Schedule to "Sesiones por hora",
            Icons.Filled.Star to "Clases individuales"
        )
        page.title.contains("Entrenamiento") -> listOf(
            Icons.Filled.Person to "Coach dedicado",
            Icons.Filled.Analytics to "Seguimiento de progreso",
            Icons.Filled.TrackChanges to "Objetivos personalizados"
        )
        else -> emptyList()
    }
}

// Modelo de datos
data class OnboardingPage(
    val title: String,
    val subtitle: String,
    val description: String,
    val isSpecial: Boolean = false,
    val isAI: Boolean = false
)

// Extensión para Text con Brush
@Composable
fun Text(
    text: String,
    brush: Brush,
    modifier: Modifier = Modifier,
    fontSize: androidx.compose.ui.unit.TextUnit = androidx.compose.ui.unit.TextUnit.Unspecified,
    fontWeight: FontWeight? = null,
    textAlign: TextAlign? = null
) {
    Text(
        text = text,
        modifier = modifier,
        fontSize = fontSize,
        fontWeight = fontWeight,
        textAlign = textAlign
    )
}

@Composable
fun Canvas(
    modifier: Modifier,
    onDraw: androidx.compose.ui.graphics.drawscope.DrawScope.() -> Unit
) {
    androidx.compose.foundation.Canvas(
        modifier = modifier,
        onDraw = onDraw
    )
}