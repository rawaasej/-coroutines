package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.theme.MyApplicationTheme
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AnimatedCircleMovement()
                }
            }
        }
    }
}

@Composable
fun AnimatedCircleMovement() {
    var isMoving by remember { mutableStateOf(false) }
    var circlePosition by remember { mutableStateOf(0f) }
    val coroutineScope = rememberCoroutineScope()

    // Valeurs animées pour la position X
    val animatedX = animateFloatAsState(
        targetValue = if (isMoving) 1000f else 0f, // Cible du mouvement
        animationSpec = androidx.compose.animation.core.tween(durationMillis = 2000)
    )

    // Valeurs animées pour la position Y
    val animatedY = animateFloatAsState(
        targetValue = if (isMoving) 1000f else 0f, // Cible du mouvement
        animationSpec = androidx.compose.animation.core.tween(durationMillis = 2000)
    )

    // Canvas pour dessiner le cercle
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawCircle(
                color = Color.Red,
                radius = 50f,
                center = androidx.compose.ui.geometry.Offset(animatedX.value, animatedY.value)
            )
        }
    }

    // Boutons pour démarrer le mouvement et réinitialiser
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = {
            // Lancer la coroutine pour déplacer le cercle avec un délai
            coroutineScope.launch {
                startMovementWithDelay { isMoving = true }
            }
        }) {
            Text("Start Movement")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = {
            // Réinitialiser la position
            isMoving = false
        }) {
            Text("Reset")
        }
    }
}

// Fonction suspendue pour simuler un délai avant de commencer le mouvement
suspend fun startMovementWithDelay(onStartMovement: () -> Unit) {
    // Simuler un délai avant de démarrer le mouvement
    delay(1000)  // attendre 1 seconde avant de démarrer le mouvement
    onStartMovement()  // Démarrer le mouvement du cercle après le délai
}

@Preview(showBackground = true)
@Composable
fun PreviewAnimatedCircleMovement() {
    MyApplicationTheme {
        AnimatedCircleMovement()
    }
}
