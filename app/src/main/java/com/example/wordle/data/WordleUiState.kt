package com.example.wordle.data

data class WordleUiState(
    val currentWord: String = "",
    val userGuess: MutableList<String> = mutableListOf(),
    val solved: Boolean = false,
    val attemptCount: Int = 0,
    val currentWordMap: Map<Char, Int> = mapOf()
)
