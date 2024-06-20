package com.example.wordle.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.wordle.data.WordleUiState
import com.example.wordle.ui.theme.Gray
import com.example.wordle.ui.theme.Green
import com.example.wordle.ui.theme.Transparent
import com.example.wordle.ui.theme.Yellow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.io.File

const val MAX_NUMBER_OF_ATTEMPTS = 6

private const val MAX_WORD_LENGTH = 5

val KEYBOARD_ROWS = listOf("QWERTYUIOP", "ASDFGHJKL", "1ZXCVBNM2")

val letterColorMap = mapOf(
    0 to Transparent, // Idle State
    1 to Gray, // Wrong State
    2 to Yellow, // Partial Correct State
    3 to Green, // Correct State
    4 to Transparent // Input State
)

class WordleViewModel: ViewModel() {

    private val _uiState = MutableStateFlow(WordleUiState())
    var uiState: StateFlow<WordleUiState> = _uiState.asStateFlow()

    private fun readFileAsLinesUsingReadLines(fileId: Int) {
        val fileName = fileId.toString()
        val words = File(fileName).readLines()
        File(fileName).writeText("")
        for (word in words) {
            if (word.length == 5)
                File(fileName).appendText(word)
        }
    }

    private fun createUserGuess(){
        repeat(MAX_NUMBER_OF_ATTEMPTS) {
            var str = ""
            repeat(MAX_WORD_LENGTH) {
                str += " "
            }
            _uiState.value.userGuess.add(str)
        }
    }

    fun updateUserGuess(char: Char){ // A to Z, 1 for ENTER and 2 for BACKSPACE
        Log.d("Check", char.toString())
    }

    init {
//        readFileAsLinesUsingReadLines(R.raw.words_alpha)
        createUserGuess()
    }

}