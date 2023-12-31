package com.hueval.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import kotlin.math.abs
import kotlin.math.roundToInt
import kotlin.random.Random

fun getRandomColour(): Color {
    val red = Random.nextInt(0, 255)
    val green = Random.nextInt(0, 255)
    val blue = Random.nextInt(0, 255)
    return Color(red, green, blue)
}

fun calculatePercentageDifference(target: Color, guess: Color): Int {
    var rDiff = abs(target.red - guess.red)
    var gDiff = abs(target.green - guess.green)
    var bDiff = abs(target.blue - guess.blue)
    return 100 - ((rDiff + bDiff + gDiff) * 100 / 3).roundToInt()
}

@Composable
fun ColourSlider(sliderPosition: Float, onValueChange: (Float) -> Unit) {
    Slider(
        value = sliderPosition,
        onValueChange,
        steps = 255,
        valueRange = 0f..1f
    )
}

@Composable
fun RgbGuess() {
    var targetColour by remember { mutableStateOf(getRandomColour()) }
    var currentGuess by remember { mutableStateOf(Color(0.5f, 0.5f, 0.5f)) }

    Column (
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Canvas(
            modifier = Modifier

        ) {
            drawRoundRect(targetColour, Offset(0F, 0F), Size(200F, 200F), CornerRadius(16f,16f))
        }

        ColourSlider(currentGuess.red, onValueChange = {x -> currentGuess = currentGuess.copy(red = x)})
        ColourSlider(currentGuess.green, onValueChange = { x -> currentGuess = currentGuess.copy(green = x)})
        ColourSlider(currentGuess.blue, onValueChange = {x -> currentGuess = currentGuess.copy(blue = x)})

        Button(onClick = {
            targetColour = getRandomColour()
            currentGuess = Color(0.5f, 0.5f, 0.5f)
        }) {
            Text("Submit Guess")
        }
        Text(calculatePercentageDifference(targetColour, currentGuess).toString())
    }
}