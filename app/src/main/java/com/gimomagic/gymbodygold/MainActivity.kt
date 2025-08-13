package com.gimomagic.gymbodygold

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.gimomagic.gymbodygold.ui.screens.loginScreens.LoginScreens
import com.gimomagic.gymbodygold.ui.screens.OnBoardingScreen
import com.gimomagic.gymbodygold.ui.theme.GymBodyGoldTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GymBodyGoldTheme {
                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation()
                }
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "onboarding") {
        composable("onboarding") {
            OnBoardingScreen(
                onSkip = {
                    // Navega a Login cuando salten el onboarding
                    navController.navigate("login") {
                        // Opcional: elimina el onboarding de la pila para que no puedan volver
                        popUpTo("onboarding") { inclusive = true }
                    }
                },
                onNext = {
                    // Aquí puedes manejar algo si quieres cuando el usuario da siguiente
                    // Por ahora no hacemos nada especial
                }
            )
        }
        composable("login") {
            LoginScreens()
        }
    }
}
