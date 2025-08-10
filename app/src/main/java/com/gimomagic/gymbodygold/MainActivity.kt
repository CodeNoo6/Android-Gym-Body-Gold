package com.gimomagic.gymbodygold

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
                    OnBoardingScreen(
                        onSkip = {
                            // Acción cuando el usuario omite el onboarding
                            println("Onboarding skipped")
                        },
                        onNext = {
                            // Acción cuando el usuario presiona "Siguiente"
                            println("Onboarding next step")
                        }
                    )
                }
            }
        }
    }
}
