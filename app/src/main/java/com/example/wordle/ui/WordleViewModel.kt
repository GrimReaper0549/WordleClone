package com.example.wordle.ui

import androidx.lifecycle.ViewModel
import com.example.wordle.R
import com.example.wordle.data.WordleUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.io.File

const val MAX_NUMBER_OF_ATTEMPTS = 6

private const val MAX_WORD_LENGTH = 5

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

    init {
//        readFileAsLinesUsingReadLines(R.raw.words_alpha)
        createUserGuess()
    }

}