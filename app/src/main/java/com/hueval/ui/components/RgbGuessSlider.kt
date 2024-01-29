package com.hueval.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Slider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

val THUMB_DIAMETER = 32.dp
val THUMB_PADDING = 3.dp
val TRACK_HEIGHT = THUMB_DIAMETER + (THUMB_PADDING * 2)

@Composable
private fun ColourGuessTrack(sliderPosition: Float, trackColour: Color) {
    Box(modifier = Modifier
        .height(TRACK_HEIGHT)
        .fillMaxWidth()
    ) {
        Box(modifier = Modifier
            .fillMaxWidth(sliderPosition)
            .height(TRACK_HEIGHT)
            .background(trackColour)
        )
    }
}

@Composable
private fun InvisibleTrack() {
    Box(modifier = Modifier
        .height(TRACK_HEIGHT)
        .fillMaxWidth()
    )
}

@Composable
private fun SelectionThumb(colour: Color) {
    Card(
        modifier = Modifier
            .size(THUMB_DIAMETER)
            .drawBehind {
                drawArc(
                    brush = Brush.radialGradient(
                        listOf(Color(0f, 0f, 0f, 0.7f), Color.Transparent),
                        radius = 24.dp.toPx(),
                        center = Offset.Unspecified
                    ),
                    startAngle = -90f,
                    sweepAngle = 180f,
                    useCenter = true,
                    topLeft = Offset(0f, -4.dp.toPx()),
                    size= Size((THUMB_DIAMETER+8.dp).toPx(), (THUMB_DIAMETER + 8.dp).toPx()),
                )
                drawCircle(
                    colour,
                    radius = (TRACK_HEIGHT/2).toPx(),
                )
            },
        shape = CircleShape,
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ){
        Box(modifier = Modifier
            .fillMaxSize()
            .background(Color(0.9f, 0.9f, 0.9f, 0.1f))
        )
    }
}

@Composable
private fun TargetThumb() {
    Box(
        modifier = Modifier
            .size(THUMB_DIAMETER)
            .clip(CircleShape)
            .background(Color(0f, 0f, 0f, 0.5f))
    )
}

enum class SliderColour {
    Red {
        override fun getColour() = Color(0.898f, 0.224f, 0.208f)
    },
    Green {
        override fun getColour() = Color(0.263f, 0.627f, 0.278f)
    },
    Blue {
        override fun getColour() = Color(0.118f, 0.533f, 0.898f)
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
            .background(
                Brush.verticalGradient(
                    colorStops =  arrayOf(
                        0f to Color(0.7f,0.7f,0.7f),
                      0.2f to Color.LightGray
                  )
                )
            ),
    ) {

        Box(modifier = Modifier
            .height(TRACK_HEIGHT)
            .width(THUMB_DIAMETER/2 + THUMB_PADDING)
            .background(sliderColour.getColour())
        )
        Slider(
            value = sliderPosition,
            onValueChange,
            modifier = Modifier.padding(THUMB_PADDING, 0.dp),
            steps = 255,
            valueRange = 0f..1f,
            thumb = { SelectionThumb(sliderColour.getColour()) },
            track = { ColourGuessTrack(sliderPosition, sliderColour.getColour()) },
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