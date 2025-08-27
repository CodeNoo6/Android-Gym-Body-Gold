package com.gimomagic.gymbodygold

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.gimomagic.gymbodygold.auth.AuthState
import com.gimomagic.gymbodygold.auth.AuthViewModel
import com.gimomagic.gymbodygold.ui.screens.HomeScreen
import com.gimomagic.gymbodygold.ui.screens.loginScreens.LoginScreens
import com.gimomagic.gymbodygold.ui.screens.OnBoardingScreen
import com.gimomagic.gymbodygold.ui.screens.loginScreens.forgotPasswordScreen.ForgotPasswordScreen
import com.gimomagic.gymbodygold.ui.theme.GymBodyGoldTheme
import com.gimomagic.gymbodygold.ui.screens.loginScreens.forgotPasswordScreen.VerificationScreen
import com.gimomagic.gymbodygold.ui.screens.loginScreens.forgotPasswordScreen.RessetPasswordScreen
import com.gimomagic.gymbodygold.ui.screens.loginScreens.forgotPasswordScreen.PasswordSuccessScreen
import com.google.firebase.FirebaseApp


class MainActivity : ComponentActivity() {
    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        FirebaseApp.initializeApp(this)

        setContent {
            GymBodyGoldTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    AppNavigation(authViewModel)
                }
            }
        }
    }
}

@Composable
fun AppNavigation(authViewModel: AuthViewModel = viewModel()) {
    val navController = rememberNavController()
    val authState by authViewModel.authState.observeAsState()

    LaunchedEffect(authState) {
        when (authState) {
            is AuthState.Authenticated -> {
                navController.navigate("home") {
                    // Limpia todo hasta el start de la gráfica
                    popUpTo(navController.graph.findStartDestination().id) { inclusive = true }
                    launchSingleTop = true
                    restoreState = true
                }
            }
            is AuthState.Unauthenticated -> {
                navController.navigate("login") {
                    popUpTo(navController.graph.findStartDestination().id) { inclusive = true }
                    launchSingleTop = true
                    restoreState = false
                }
            }
            else -> Unit
        }
    }

    val startDestination = when (authState) {
        is AuthState.Authenticated -> "home"
        is AuthState.Unauthenticated -> "onboarding"
        else -> "onboarding"
    }

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable("onboarding") {
            OnBoardingScreen(
                onComplete = {
                    navController.navigate("login") {
                        popUpTo("onboarding") { inclusive = true }
                    }
                },
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
            LoginScreens(
                authViewModel = authViewModel,
                onForgotPasswordClick = { navController.navigate("forgot_password") }
            )
        }

        composable("home") {
            HomeScreen(
                onLogout = {
                    authViewModel.signOut()
                }
            )
        }

        composable("forgot_password") {
            ForgotPasswordScreen(
                onBack = { navController.popBackStack() },
                onVerify = { email ->
                    navController.navigate("verification/$email")
                }
            )
        }

        composable("verification/{email}") { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email") ?: ""
            VerificationScreen(
                email = email,
                onBack = { navController.popBackStack() },
                onVerify = {
                    navController.navigate("reset_password")
                }
            )
        }

        composable("reset_password") {
            RessetPasswordScreen(
                onBack = { navController.popBackStack() },
                onPasswordReset = {
                    navController.navigate("password_success") {
                        popUpTo("reset_password") { inclusive = true }
                    }
                }
            )
        }
        composable("password_success") {
            PasswordSuccessScreen(
                onBack = { navController.popBackStack() },
                onContinue = {
                    navController.navigate("login") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            )
        }

    }
}
