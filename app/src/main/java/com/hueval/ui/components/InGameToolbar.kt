package com.hueval.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun InGameToolbar(
    onHomeButtonClick: () -> Unit,
    onInfoButtonClick: () -> Unit,
) {
    val iconModifier = Modifier.padding(8.dp)
    val iconColour = Color.Gray
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(
            onHomeButtonClick,
            modifier = iconModifier
        ) {
            Icon(
                Icons.Outlined.Home,
                contentDescription = "Return to home screen",
                tint = iconColour
            )
        }
        IconButton(
            onInfoButtonClick,
            modifier = iconModifier
        ) {
            Icon(
                Icons.Outlined.Info,
                contentDescription = "Display game explanation",
                tint = iconColour
            )
        }
    }
}