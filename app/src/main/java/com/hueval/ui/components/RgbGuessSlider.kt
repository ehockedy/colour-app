package com.hueval.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Slider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

val THUMB_DIAMETER = 32.dp
val THUMB_PADDING = 8.dp
val TRACK_HEIGHT = THUMB_DIAMETER + (THUMB_PADDING * 2)

@Composable
private fun InvisibleTrack() {
    Box(modifier = Modifier
        .fillMaxWidth()
    )
}

@Composable
private fun SelectionThumb() {
    Box(
        modifier = Modifier
            .width(THUMB_DIAMETER)
            .height(THUMB_DIAMETER)
            .clip(CircleShape)
            .background(Color.LightGray)
    )
}

@Composable
private fun TargetThumb() {
    Box(
        modifier = Modifier
            .width(THUMB_DIAMETER)
            .height(THUMB_DIAMETER)
            .clip(CircleShape)
            .background(Color(0f, 0f, 0f, 0.5f))
    )
}

enum class SliderColour {
    Red {
        override fun getColour() = Color.Red
    },
    Green {
        override fun getColour() = Color.Green
    },
    Blue {
        override fun getColour() = Color.Blue
    };
    abstract fun getColour(): Color
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun RgbGuessSlider(
    sliderColour: SliderColour,
    sliderPosition: Float,
    targetPosition: Float,
    isTargetVisible: Boolean,
    onValueChange: (Float) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(TRACK_HEIGHT)
            .padding(32.dp, 0.dp)
            .clip(CircleShape)
            .border(
                4.dp,
                Brush.horizontalGradient(listOf(Color.Black, sliderColour.getColour())),
                CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        Slider(
            value = sliderPosition,
            onValueChange,
            modifier = Modifier.padding(THUMB_PADDING, 0.dp),
            steps = 255,
            valueRange = 0f..1f,
            thumb = { SelectionThumb() },
            track = { InvisibleTrack() }
        )
        AnimatedVisibility(
            isTargetVisible,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Slider(
                value = targetPosition,
                onValueChange = {},
                modifier = Modifier.padding(THUMB_PADDING, 0.dp),
                steps = 255,
                valueRange = 0f..1f,
                thumb = { TargetThumb() },
                track = { InvisibleTrack() }
            )
        }


    }
}