package com.hueval.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp

/**
 * Source: https://blog.canopas.com/jetpack-compose-cool-button-click-effects-c6bbecec7bcb
 */
enum class ButtonState { Pressed, Idle }
fun Modifier.bounceClick(onClick: (() -> Unit)? = null) = composed {
    var buttonState by remember { mutableStateOf(ButtonState.Idle) }
    val scale by animateFloatAsState(
        if (buttonState == ButtonState.Pressed) 0.95f else 1f,
        animationSpec = tween(durationMillis = 100),
        label = "bounceClick"
    )

    this
        .graphicsLayer {
            scaleX = scale
            scaleY = scale
        }
        .clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = null,
            onClick = onClick ?: {}
        )
        .pointerInput(buttonState) {
            awaitPointerEventScope {
                buttonState = if (buttonState == ButtonState.Pressed) {
                    waitForUpOrCancellation()
                    ButtonState.Idle
                } else {
                    awaitFirstDown(false)
                    ButtonState.Pressed
                }
            }
        }
}


@Composable
fun ElevatedButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable() (RowScope.() -> Unit)
) {
    Button(
        modifier = modifier.bounceClick(),
        onClick = onClick,
        contentPadding = PaddingValues(16.dp, 8.dp),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 4.dp,
            pressedElevation = 1.dp
        )
    ){
        content()
    }
}