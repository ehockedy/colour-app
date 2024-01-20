package com.hueval.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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

fun percentageDiffToResult(diff: Int): String {
    if (diff == 100) {
        return "Perfect!"
    } else if (diff >= 95) {
        return "Amazing!"
    } else if (diff >= 85) {
        return "Good job!"
    } else if (diff >= 75) {
        return "Not bad!"
    } else if (diff >= 50) {
        return "Close...ish."
    }
    return "Oh dear!"
}

@Composable
fun RgbGuess() {
    var isResultDisplayed by remember { mutableStateOf(false)}  // TODO replace with state enum?
    val userGuessVisibleState = remember {  MutableTransitionState(false) }
    val resultsMessageVisibleState = remember {  MutableTransitionState(false) }

    var targetColour by remember { mutableStateOf(getRandomColour()) }
    var currentGuess by remember { mutableStateOf(Color(0.5f, 0.5f, 0.5f)) }

    Column (
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Column (modifier = Modifier.width(IntrinsicSize.Max), horizontalAlignment = Alignment.CenterHorizontally) {
            val hideTarget = isResultDisplayed && !userGuessVisibleState.isIdle && !userGuessVisibleState.targetState
            Row {
                AnimatedVisibility(!hideTarget,
                    enter = fadeIn(
                        animationSpec = tween(200, 0)
                    ),
                    exit = fadeOut(
                        animationSpec = tween(500, 0)
                    )
                ) {
                    ColourBox(targetColour, "Target")
                }
                AnimatedVisibility(
                    userGuessVisibleState,
                    enter = expandHorizontally(
                        animationSpec = tween(500, 0)
                    ) + fadeIn(
                        animationSpec = tween(500, 500)
                    ),
                    exit = fadeOut(
                        animationSpec = tween(500, 0)
                    ) + shrinkHorizontally(
                        animationSpec = tween(500, 500),
                    )
                ) {
                    ColourBox(currentGuess, "Your Guess")
                }
            }

            val percentageDiff = calculatePercentageDifference(targetColour, currentGuess)
            Row (modifier = Modifier
                .height(20.dp)
                .width(IntrinsicSize.Max)) {
                AnimatedVisibility(
                    resultsMessageVisibleState,
                    enter = fadeIn(),
                    exit = ExitTransition.None
                ) {
                    Text(percentageDiffToResult(percentageDiff) + " You got a " + percentageDiff.toString() + "% match.")
                }
            }
        }

        ColourSlider(currentGuess.red, onValueChange = {x -> currentGuess = currentGuess.copy(red = x)})
        ColourSlider(currentGuess.green, onValueChange = { x -> currentGuess = currentGuess.copy(green = x)})
        ColourSlider(currentGuess.blue, onValueChange = {x -> currentGuess = currentGuess.copy(blue = x)})

        Button(onClick = {
            // On "reset"
            if (isResultDisplayed) {
                // Start transitioning components back to guessing state
                userGuessVisibleState.targetState = false
                resultsMessageVisibleState.targetState = false
            }

            // On "submit"
            if (!isResultDisplayed) {
                // Start transitioning components to results state
                isResultDisplayed = true
                userGuessVisibleState.targetState = true
                resultsMessageVisibleState.targetState = true
            }
        }, Modifier.width(150.dp)) {
            Text(if (isResultDisplayed) "Next Colour" else "Submit Guess")
        }

        // Check whether all components have transitioned back to guessing state
        // If so, reset the colour and enter guessing state
        if (isResultDisplayed
            && userGuessVisibleState.isIdle && !userGuessVisibleState.currentState
            && resultsMessageVisibleState.isIdle && !resultsMessageVisibleState.currentState
        ) {
            targetColour = getRandomColour()
            currentGuess = Color(0.5f, 0.5f, 0.5f)
            isResultDisplayed = false
        }
    }
}