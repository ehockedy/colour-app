package com.hueval.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.animateColorAsState
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
import com.hueval.ui.components.RgbGuessSlider
import com.hueval.ui.components.SliderColour
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

val STARTING_COLOUR = Color(0.5f, 0.5f, 0.5f)

// Timings for each part of the transition between states
const val CLEAR_RESULTS_TRANSITION_MS = 400
const val REVEAL_USER_GUESS_TRANSITION_MS = 400
const val SHOW_NEW_TARGET_TRANSITION_MS = 400
const val RESET_SLIDERS_TRANSITION_MS = 700

@Composable
fun RgbGuess() {
    var isResultDisplayed by remember { mutableStateOf(false)}  // TODO replace with state enum?
    var isSliderResetActive by remember { mutableStateOf(false)}
    var isTargetColourResultShown by remember { mutableStateOf(false)}
    val userGuessVisibleState = remember {  MutableTransitionState(false) }
    val resultsMessageVisibleState = remember {  MutableTransitionState(false) }

    var targetColour by remember { mutableStateOf(getRandomColour()) }
    var currentGuess by remember { mutableStateOf(STARTING_COLOUR) }
    var finalGuess by remember { mutableStateOf(STARTING_COLOUR) }
    val resettableGuess by animateColorAsState(
        if (isSliderResetActive) STARTING_COLOUR else currentGuess,
        label = "resettableGuess",
        animationSpec =  tween(RESET_SLIDERS_TRANSITION_MS),
        finishedListener = {isSliderResetActive = false}
    )

    Column (
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Column (modifier = Modifier.width(IntrinsicSize.Max), horizontalAlignment = Alignment.CenterHorizontally) {
            // Hide the target colour while it is being reset
            val hideTarget = isResultDisplayed && !userGuessVisibleState.isIdle && !userGuessVisibleState.targetState
            Row {
                AnimatedVisibility(!hideTarget,
                    enter = fadeIn(
                        animationSpec = tween(SHOW_NEW_TARGET_TRANSITION_MS, 0)
                    ),
                    exit = fadeOut(
                        animationSpec = tween(CLEAR_RESULTS_TRANSITION_MS, 0)
                    )
                ) {
                    ColourBox(targetColour, "Target")
                }
                AnimatedVisibility(
                    userGuessVisibleState,
                    enter = expandHorizontally(
                        animationSpec = tween(REVEAL_USER_GUESS_TRANSITION_MS, 0)
                    ) + fadeIn(
                        animationSpec = tween(REVEAL_USER_GUESS_TRANSITION_MS, REVEAL_USER_GUESS_TRANSITION_MS)
                    ),
                    exit = fadeOut(
                        animationSpec = tween(CLEAR_RESULTS_TRANSITION_MS, 0)
                    ) + shrinkHorizontally(
                        animationSpec = tween(CLEAR_RESULTS_TRANSITION_MS, CLEAR_RESULTS_TRANSITION_MS),
                    )
                ) {
                    ColourBox(finalGuess, "Your Guess")
                }
            }

            val percentageDiff = calculatePercentageDifference(targetColour, finalGuess)
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

        Column(
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            RgbGuessSlider(
                SliderColour.Red,
                if (isResultDisplayed) resettableGuess.red else currentGuess.red,
                targetColour.red,
                isTargetColourResultShown
            ) { x ->
                if (!isResultDisplayed) {
                    currentGuess = currentGuess.copy(red = x)
                }
            }
            RgbGuessSlider(
                SliderColour.Green,
                if (isResultDisplayed) resettableGuess.green else currentGuess.green,
                targetColour.green,
                isTargetColourResultShown
            ) { x ->
                if (!isResultDisplayed) {
                    currentGuess = currentGuess.copy(green = x)
                }
            }
            RgbGuessSlider(
                SliderColour.Blue,
                if (isResultDisplayed) resettableGuess.blue else currentGuess.blue,
                targetColour.blue,
                isTargetColourResultShown
            ) { x ->
                if (!isResultDisplayed) {
                    currentGuess = currentGuess.copy(blue = x)
                }
            }
        }

        Button(onClick = {
            // On "reset"
            if (isResultDisplayed) {
                // Start transitioning components back to guessing state
                userGuessVisibleState.targetState = false
                resultsMessageVisibleState.targetState = false
                isTargetColourResultShown = false

                // Start transition to reset sliders
                if (currentGuess != STARTING_COLOUR) {
                    currentGuess = STARTING_COLOUR
                    isSliderResetActive = true
                }
            }

            // On "submit"
            if (!isResultDisplayed) {
                // Start transitioning components to results state
                finalGuess = currentGuess
                isResultDisplayed = true
                userGuessVisibleState.targetState = true
                resultsMessageVisibleState.targetState = true
                isTargetColourResultShown = true
            }
        }, Modifier.width(150.dp)) {
            Text(if (isResultDisplayed) "Next Colour" else "Submit Guess")
        }

        // Check whether all components have transitioned back to guessing state
        // If so, reset the colour and enter guessing state
        if (isResultDisplayed
            && userGuessVisibleState.isIdle && !userGuessVisibleState.currentState
            && resultsMessageVisibleState.isIdle && !resultsMessageVisibleState.currentState
            && !isSliderResetActive
        ) {
            targetColour = getRandomColour()
            isResultDisplayed = false
        }
    }
}