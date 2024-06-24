package com.example.wordle.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.wordle.ui.theme.WordleTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.wordle.data.WordleScreens

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WordleApp(
    viewModel: WordleViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
){
    val uiState by viewModel.uiState.collectAsState()
    val backStackEntry by navController.currentBackStackEntryAsState()

    Box(modifier = Modifier
        .fillMaxSize()
        .wrapContentHeight(Alignment.CenterVertically)
        .wrapContentWidth(Alignment.CenterHorizontally)
    ){
        NavHost(navController = navController,
            startDestination = WordleScreens.GamePage.name) {
            composable(WordleScreens.LandingPage.name){
                LandingPage(
                    onPlayButtonClicked = {
                        navController.navigate(WordleScreens.GamePage.name)
                    },
                    onHowToPlayClicked = {
                        navController.navigate(WordleScreens.HowToPlayPage.name)
                    }
                )
            }
            composable(WordleScreens.GamePage.name){
                GamePage(uiState = uiState,
                    onKeyboardKeyClick = { viewModel.updateUserGuess(it) },
                    keyboardColorMap = uiState.keyboardColorMap
                )
            }
            composable(WordleScreens.HowToPlayPage.name){
                HowToPlayCard(
                    onPlayButtonClicked = {
                        navController.navigate(WordleScreens.GamePage.name)
                    },
                    navigateUp = { navController.navigateUp() }
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun WordleAppPreview(){
    WordleTheme {
        WordleApp()
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun WordleAppPreviewDark(){
    WordleTheme(darkTheme = true) {
        WordleApp()
    }
}