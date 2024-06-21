package com.example.wordle.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.wordle.data.WORDLE_WORD_LIST
import com.example.wordle.data.WordleUiState
import com.example.wordle.ui.theme.Gray
import com.example.wordle.ui.theme.Green
import com.example.wordle.ui.theme.Transparent
import com.example.wordle.ui.theme.Yellow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

const val MAX_NUMBER_OF_ATTEMPTS = 6

private const val MAX_WORD_LENGTH = 5

val KEYBOARD_ROWS = listOf(
    "QWERTYUIOP",
    "ASDFGHJKL",
    "1ZXCVBNM2"
)

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

    private fun createUserGuess(){
        repeat(MAX_NUMBER_OF_ATTEMPTS) {
            var str = ""
            repeat(MAX_WORD_LENGTH) {
                str += " "
            }
            _uiState.value.userGuess.add(str)
        }
        repeat(MAX_NUMBER_OF_ATTEMPTS) {
            var str = ""
            repeat(MAX_WORD_LENGTH) {
                str += "0"
            }
            _uiState.value.userGuessColors.add(str)
        }
    }

    private fun initialiseWord(){
        val currentWord = WORDLE_WORD_LIST.random()
        val currentWordMap = _uiState.value.currentWordMap
        for(letter in currentWord){
            currentWordMap[letter]?.inc()
        }
        _uiState.update { currentState->
            currentState.copy(
                currentWord = currentWord,
                currentWordMap = currentWordMap
            )
        }
        Log.d("Check", currentWord)
        currentWordMap.forEach { (key, value) -> Log.d("Check", key.toString()+value.toString()) }
    }

    private fun resetGame(){

    }

    private fun isAValidWord(str: String): Boolean{
        return if(_uiState.value.currentLetter == MAX_WORD_LENGTH){
            if(WORDLE_WORD_LIST.indexOf(str) != -1){
                true
            } else{
    //                Word Not on List
                false
            }
        } else{
    //            Not Enough Letters
            false
        }
    }

    fun updateUserGuess(char: Char) { // A to Z, 1 for ENTER and 2 for BACKSPACE
        when(char){
            '1'-> {
                if (isAValidWord(_uiState.value.userGuess[_uiState.value.attemptCount])) {
                    if(_uiState.value.userGuess[_uiState.value.attemptCount] == _uiState.value.currentWord){
//                        Solved(Message for how many tries taken)
                            resetGame()
                    }
                    _uiState.update { currentState ->
                        currentState.copy(
                            attemptCount = _uiState.value.attemptCount.inc(),
                            currentLetter = 0
                        )
                    }
                }
            }
            '2'-> {
                if(_uiState.value.currentLetter>0){
                    val userChoices = _uiState.value.userGuess
                    var str = userChoices[_uiState.value.attemptCount].slice(0..<_uiState.value.currentLetter-1)
                    repeat(MAX_WORD_LENGTH - _uiState.value.currentLetter+1) {
                        str += " "
                    }
                    userChoices[_uiState.value.attemptCount] = str
                    _uiState.update { currentState ->
                        currentState.copy(
                            currentLetter = _uiState.value.currentLetter.dec(),
                            userGuess = userChoices
                        )
                    }
                }
            }
            else-> {
                if(_uiState.value.currentLetter < MAX_WORD_LENGTH) {
                    val userChoices = _uiState.value.userGuess
                    var str = userChoices[_uiState.value.attemptCount].slice(0..<_uiState.value.currentLetter) + char
                    repeat(MAX_WORD_LENGTH - _uiState.value.currentLetter - 1) {
                        str += " "
                    }
                    userChoices[_uiState.value.attemptCount] = str
                    _uiState.update { currentState ->
                        currentState.copy(
                            currentLetter = _uiState.value.currentLetter.inc(),
                            userGuess = userChoices
                        )
                    }
                }
            }
        }
    }

    init {
        initialiseWord()
        createUserGuess()
    }

}