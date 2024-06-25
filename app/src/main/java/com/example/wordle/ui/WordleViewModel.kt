package com.example.wordle.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.wordle.data.WORDLE_WORD_LIST
import com.example.wordle.data.WordleUiState
import com.example.wordle.ui.theme.Gray
import com.example.wordle.ui.theme.Green
import com.example.wordle.ui.theme.Keyboard_Letters_Background_Color
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

val keyboardLettersColorMap = mapOf(
    0 to Keyboard_Letters_Background_Color, // Idle State
    1 to Gray, // Wrong State
    2 to Yellow, // Partial Correct State
    3 to Green, // Correct State
)

class WordleViewModel: ViewModel() {

    private val _uiState = MutableStateFlow(WordleUiState())
    var uiState: StateFlow<WordleUiState> = _uiState.asStateFlow()

    private fun initialiseTheGame(){
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
//        repeat(MAX_NUMBER_OF_ATTEMPTS) {
//            _uiState.value.solvedCount.add(0)
//        }
        initialiseWord()
    }

    private fun initialiseWord(){
        val currentWord = WORDLE_WORD_LIST.random()
        val currentWordMap = _uiState.value.currentWordMap.toMutableMap()
        for(letter in currentWord){
            currentWordMap[letter] = currentWordMap[letter]!!.plus(1)
        }
        _uiState.update { currentState->
            currentState.copy(
                currentWord = currentWord,
                currentWordMap = currentWordMap
            )
        }
        Log.d("Check", currentWord)
    }

    private fun initialiseUiState(){
        _uiState.update { currentState->
            currentState.copy(
            currentWord = "",
            userGuess = mutableListOf(),
            userGuessColors = mutableListOf(),
            solved = false,
            attemptCount = 0,
            currentWordMap = mutableMapOf(
                'A' to 0,
                'B' to 0,
                'C' to 0,
                'D' to 0,
                'E' to 0,
                'F' to 0,
                'G' to 0,
                'H' to 0,
                'I' to 0,
                'J' to 0,
                'K' to 0,
                'L' to 0,
                'M' to 0,
                'N' to 0,
                'O' to 0,
                'P' to 0,
                'Q' to 0,
                'R' to 0,
                'S' to 0,
                'T' to 0,
                'U' to 0,
                'V' to 0,
                'W' to 0,
                'X' to 0,
                'Y' to 0,
                'Z' to 0
            ),
            currentLetter = 0,
            correctGuess = false,
            keyboardColorMap = mutableMapOf(
                'A' to 0,
                'B' to 0,
                'C' to 0,
                'D' to 0,
                'E' to 0,
                'F' to 0,
                'G' to 0,
                'H' to 0,
                'I' to 0,
                'J' to 0,
                'K' to 0,
                'L' to 0,
                'M' to 0,
                'N' to 0,
                'O' to 0,
                'P' to 0,
                'Q' to 0,
                'R' to 0,
                'S' to 0,
                'T' to 0,
                'U' to 0,
                'V' to 0,
                'W' to 0,
                'X' to 0,
                'Y' to 0,
                'Z' to 0,
                '1' to 0,
                '2' to 0
            )
        ) }
    }

    fun resetGame(){
        Log.d("Check", "RESET")
//        initialiseUiState()
        initialiseTheGame()
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

    private fun solved(){
//        val updatedSolvedCount = _uiState.value.solvedCount
//        updatedSolvedCount[_uiState.value.attemptCount].plus(1)
//        _uiState.update { currentState->
//            currentState.copy(solvedCount = updatedSolvedCount)
//        }
//        _uiState.value.solvedCount.forEach {
//            Log.d("Check", it.toString())
//        }
        _uiState.update { currentState->
            currentState.copy(solved = true)
        }
    }

    private fun setColorsOnWordEntered(): String{
        val currentGuess = _uiState.value.userGuess[_uiState.value.attemptCount]
        var str = ""
        val currentWord = _uiState.value.currentWord
        val currentWordMap = _uiState.value.currentWordMap.toMutableMap()
        for(i in currentGuess.indices) {
            if(currentGuess[i] == currentWord[i]){
                currentWordMap[currentGuess[i]] = currentWordMap[currentGuess[i]]!!.plus(-1)
                if(_uiState.value.keyboardColorMap[currentGuess[i]]!! < 3) {
                    _uiState.value.keyboardColorMap[currentGuess[i]] = 3
                }
                str += "3"
            }
            else if(currentWordMap[currentGuess[i]]!! > 0) {
                currentWordMap[currentGuess[i]] = currentWordMap[currentGuess[i]]!!.plus(-1)
                if(_uiState.value.keyboardColorMap[currentGuess[i]]!! < 2) {
                    _uiState.value.keyboardColorMap[currentGuess[i]] = 2
                }
                str += "2"
            }
            else {
                if(_uiState.value.keyboardColorMap[currentGuess[i]]!! < 1) {
                    _uiState.value.keyboardColorMap[currentGuess[i]] = 1
                }
                str += "1"
            }
        }
        return str
    }

    fun updateUserGuess(char: Char) { // A to Z, 1 for ENTER and 2 for BACKSPACE
        if(!_uiState.value.solved){
            when(char){
                '1'-> {
                    if (isAValidWord(_uiState.value.userGuess[_uiState.value.attemptCount])) {
                        var str=""
                        val userGuessColors = _uiState.value.userGuessColors.toMutableList()
                        if(_uiState.value.userGuess[_uiState.value.attemptCount] == _uiState.value.currentWord){
//                        Solved(Message for how many tries taken)
                            repeat(MAX_WORD_LENGTH){
                                str+="3"
                            }
                            solved()
//                            return
//                            resetGame()
                        }
                        else{
                            str = setColorsOnWordEntered()
                        }
                        userGuessColors[_uiState.value.attemptCount]=str
                        _uiState.update { currentState ->
                            currentState.copy(
                                attemptCount = _uiState.value.attemptCount.inc(),
                                currentLetter = 0,
                                userGuessColors = userGuessColors
                            )
                        }
                    }
                }
                '2'-> {
                    if(_uiState.value.currentLetter>0){
                        val userChoices = _uiState.value.userGuess.toMutableList()
                        val userChoiceColors = _uiState.value.userGuessColors.toMutableList()
                        var str = userChoices[_uiState.value.attemptCount].slice(0..<_uiState.value.currentLetter-1)
                        repeat(MAX_WORD_LENGTH - _uiState.value.currentLetter+1) {
                            str += " "
                        }
                        userChoices[_uiState.value.attemptCount] = str
                        str = userChoiceColors[_uiState.value.attemptCount].slice(0..<_uiState.value.currentLetter-1)
                        repeat(MAX_WORD_LENGTH - _uiState.value.currentLetter+1) {
                            str += "0"
                        }
                        userChoiceColors[_uiState.value.attemptCount] = str
                        _uiState.update { currentState ->
                            currentState.copy(
                                currentLetter = _uiState.value.currentLetter.dec(),
                                userGuess = userChoices,
                                userGuessColors = userChoiceColors
                            )
                        }
                    }
                }
                else-> {
                    if(_uiState.value.currentLetter < MAX_WORD_LENGTH) {
                        val userChoices = _uiState.value.userGuess.toMutableList()
                        val userChoiceColors = _uiState.value.userGuessColors.toMutableList()
                        var str = userChoices[_uiState.value.attemptCount].slice(0..<_uiState.value.currentLetter) + char
                        repeat(MAX_WORD_LENGTH - _uiState.value.currentLetter - 1) {
                            str += " "
                        }
                        userChoices[_uiState.value.attemptCount] = str
                        str = userChoiceColors[_uiState.value.attemptCount].slice(0..<_uiState.value.currentLetter) + "4"
                        repeat(MAX_WORD_LENGTH - _uiState.value.currentLetter - 1) {
                            str += "0"
                        }
                        userChoiceColors[_uiState.value.attemptCount] = str
                        _uiState.update { currentState ->
                            currentState.copy(
                                currentLetter = _uiState.value.currentLetter.inc(),
                                userGuess = userChoices,
                                userGuessColors = userChoiceColors
                            )
                        }
                    }
                }
            }
        }
    }

    init {
        initialiseTheGame()
    }

}