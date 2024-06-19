package com.example.wordle

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.wordle.ui.WordleApp
import com.example.wordle.ui.theme.WordleTheme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WordleTheme {
                WordleApp()
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun WordleAppPreview() {
    WordleTheme {
        WordleApp()
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = false)
@Composable
fun WordleAppPreviewDark() {
    WordleTheme(darkTheme = true) {
        WordleApp()
    }
}



// Add How to play accessibility, and activate the close button
// Develop the Playing Page