package com.hueval.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import kotlin.random.Random

fun getRandomColour(): Color {
    val red = Random.nextInt(0, 255)
    val green = Random.nextInt(0, 255)
    val blue = Random.nextInt(0, 255)
    return Color(red, green, blue)
}

@Composable
fun RgbGuess() {
    var colourCode by remember { mutableStateOf(getRandomColour()) }

    Canvas(
        modifier = Modifier
            .fillMaxSize()
    ) {
        drawRoundRect(colourCode, Offset(0F, 0F), Size(200F, 200F))
    }

    Button(onClick = { colourCode = getRandomColour() }) {
        Text(colourCode.toString())
    }
}