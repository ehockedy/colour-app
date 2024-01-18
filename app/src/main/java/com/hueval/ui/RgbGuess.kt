package com.hueval.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
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
    val rDiff = abs(target.red - guess.red)
    val gDiff = abs(target.green - guess.green)
    val bDiff = abs(target.blue - guess.blue)
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
fun ColourBox(colour: Color, text: String) {
    return Column(
        modifier = Modifier.padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text)
        Box(modifier = Modifier
            .padding(PaddingValues(0.dp, 8.dp))
            .clip(RoundedCornerShape(8.dp))
            .background(colour)
            .size(100.dp)
        )
    }
}

@Composable
fun RgbGuess() {
    var isResultDisplayed by remember { mutableStateOf(false)}
    var targetColour by remember { mutableStateOf(getRandomColour()) }
    var currentGuess by remember { mutableStateOf(Color(0.5f, 0.5f, 0.5f)) }

    Column (
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Row {
            ColourBox(targetColour, "Target:")
            AnimatedVisibility(
                isResultDisplayed,
                enter = expandHorizontally() + fadeIn() ,
                exit = shrinkHorizontally(shrinkTowards = Alignment.End) + fadeOut()
            ) {
                ColourBox(currentGuess, "Your Guess:")
            }
        }

        ColourSlider(currentGuess.red, onValueChange = {x -> currentGuess = currentGuess.copy(red = x)})
        ColourSlider(currentGuess.green, onValueChange = { x -> currentGuess = currentGuess.copy(green = x)})
        ColourSlider(currentGuess.blue, onValueChange = {x -> currentGuess = currentGuess.copy(blue = x)})

        Button(onClick = {
            targetColour = getRandomColour()
            currentGuess = Color(0.5f, 0.5f, 0.5f)
            isResultDisplayed = !isResultDisplayed
        }) {
            Text("Submit Guess")
        }
        Text(calculatePercentageDifference(targetColour, currentGuess).toString())
    }
}