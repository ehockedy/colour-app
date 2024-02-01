package com.hueval.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.hueval.ui.components.ElevatedButton

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
        Text("Hueval game")
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            ElevatedButton(onClick = onRgbGuessButtonClicked) {
                Text("RGB Guess") // TODO use text key
            }
            ElevatedButton(onClick = onValueMatchButtonClicked) {
                Text("Value Match")
            }
        }
    }
}