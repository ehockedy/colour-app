package com.hueval

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.hueval.ui.MainMenu
import com.hueval.ui.RgbGuess
import com.hueval.ui.ValueMatch

enum class HuevalScreen() {
    MainMenu,
    RgbGuess,
    ValueMatch,
}
@Composable
fun HuevalApp(
//    viewModel: OrderViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = HuevalScreen.MainMenu.name,
        modifier = Modifier
    ) {
        composable(route = HuevalScreen.MainMenu.name) {
            MainMenu(
                onRgbGuessButtonClicked = {
                    navController.navigate(HuevalScreen.RgbGuess.name)
                },
                onValueMatchButtonClicked = {
                    navController.navigate(HuevalScreen.ValueMatch.name)
                }
            )
        }
        composable(route = HuevalScreen.RgbGuess.name) {
            RgbGuess(
                onHomeButtonClick = {
                    navController.navigate(HuevalScreen.MainMenu.name)
                }
            )
        }
        composable(route = HuevalScreen.ValueMatch.name) {
            ValueMatch()
        }
    }
}