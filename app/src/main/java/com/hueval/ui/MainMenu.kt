package com.hueval.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.hueval.R
import com.hueval.ui.components.ElevatedButton
import com.hueval.ui.components.bounceClick

@Composable
fun MainMenu(
    onRgbGuessButtonClicked: () -> Unit,
    onValueMatchButtonClicked: () -> Unit,
) {
    Column (
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(painter = painterResource(id = R.drawable.mainlogo),
            "Main logo",
            modifier = Modifier.size(400.dp, 250.dp)
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Image(painter = painterResource(id = R.drawable.rgbguesslogo),
                "RGB Guess option",
                modifier = Modifier
                    .size(180.dp)
                    .bounceClick(onRgbGuessButtonClicked)
            )
            Image(painter = painterResource(id = R.drawable.valueguesslogo),
                "Value Guess option",
                modifier = Modifier
                    .size(180.dp)
                    .bounceClick(onValueMatchButtonClicked)
            )
        }
    }
}