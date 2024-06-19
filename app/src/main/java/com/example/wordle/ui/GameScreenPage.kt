package com.example.wordle.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.wordle.R
import com.example.wordle.data.WordleUiState
import com.example.wordle.ui.theme.Black
import com.example.wordle.ui.theme.Box_Border
import com.example.wordle.ui.theme.Gray
import com.example.wordle.ui.theme.Green
import com.example.wordle.ui.theme.LightColorScheme
import com.example.wordle.ui.theme.White
import com.example.wordle.ui.theme.Yellow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun WordleTopAppBar(){
    CenterAlignedTopAppBar(title = {
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.wordle_logo),
                    contentDescription = null,
                    modifier = Modifier.size(dimensionResource(id = R.dimen.logo_size))
                )
                Spacer(modifier = Modifier.weight(1F))
                Text(
                    text = stringResource(id = R.string.app_name),
                    style = MaterialTheme.typography.displaySmall
                )
                Spacer(modifier = Modifier.weight(1F))
                Icon(imageVector = Icons.Filled.Menu,
                    contentDescription = null,
                    modifier = Modifier
                        .size(dimensionResource(id = R.dimen.logo_size))
                )
//                Row(modifier = Modifier.padding(end = dimensionResource(id = R.dimen.extra_small_padding))) {
//                    Icon(
//                        painter = painterResource(id = R.drawable.help),
//                        contentDescription = stringResource(id = R.string.how_to_play),
//                        modifier = Modifier
//                            .size(dimensionResource(id = R.dimen.logo_size))
//                            .padding(end = dimensionResource(id = R.dimen.extra_small_padding))
//                    )
//                    Icon(
//                        painter = painterResource(id = R.drawable.leaderboard),
//                        contentDescription = stringResource(id = R.string.stats),
//                        modifier = Modifier
//                            .size(dimensionResource(id = R.dimen.logo_size))
//                            .padding(end = dimensionResource(id = R.dimen.extra_small_padding))
//                    )
//                    Icon(
//                        imageVector = Icons.Filled.Settings,
//                        contentDescription = stringResource(id = R.string.settings),
//                        modifier = Modifier
//                            .size(dimensionResource(id = R.dimen.logo_size))
//                            .padding(end = dimensionResource(id = R.dimen.extra_small_padding))
//                    )
//                }
            }
            Divider(
                thickness = 1.5.dp,
                modifier = Modifier.padding(dimensionResource(id = R.dimen.small_padding)),
                color = MaterialTheme.colorScheme.outline
            )
        }
    }, colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background))
}

private val colorMap = mapOf(
    0 to Color.Transparent,
    1 to Gray,
    2 to Yellow,
    3 to Green
)

@Composable
private fun WordInBlock(word: List<Char>){
    for( i in word.indices) {
        val backgroundColor = colorMap.getValue(0)
        Text(
            text = "${word[i]}",
            modifier = Modifier
                .padding(
                    start = dimensionResource(id = R.dimen.extra_small_padding),
                    end = dimensionResource(id = R.dimen.extra_small_padding)
                )
                .background(color = backgroundColor)
                .border(
                    shape = RectangleShape,
                    width = dimensionResource(id = R.dimen.box_border_width),
                    color = Box_Border
                )
                .size(dimensionResource(id = R.dimen.word_box_size))
                .wrapContentHeight(Alignment.CenterVertically)
                .wrapContentWidth(Alignment.CenterHorizontally),
            style = MaterialTheme.typography.bodyLarge,
            color = when(backgroundColor == Color.Transparent){
                true-> when(MaterialTheme.colorScheme.surfaceVariant == LightColorScheme.surfaceVariant){
                    true-> Black
                    else-> White
                }
                else-> White
            }
        )
    }
}


@Composable
fun GamePage(
    uiState: WordleUiState
) {
    Scaffold(
        topBar = { WordleTopAppBar() }
    ) { innerPadding ->
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)){
            Column {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = dimensionResource(id = R.dimen.large_padding))
                ) {
                    repeat(MAX_NUMBER_OF_ATTEMPTS) {
                        Row(
                            modifier = Modifier
                                .padding(dimensionResource(id = R.dimen.small_padding))
                        ) {
                            WordInBlock(word = uiState.userGuess[it].toList())
                        }
                    }
                }
                Column {
                    val keyboardRows = listOf("QWERTYUIOP", "ASDFGHJKL", "ZXCVBNM")
                    repeat(3) {
                        Row {
                            if(it == 3) {
                                Text(
                                    text = "ENTER",
                                    modifier = Modifier
                                        .padding(
                                            start = dimensionResource(id = R.dimen.extra_small_padding),
                                            end = dimensionResource(id = R.dimen.extra_small_padding)
                                        )
                                        .background(color = Gray)
                                        .border(
                                            shape = RectangleShape,
                                            width = dimensionResource(id = R.dimen.box_border_width),
                                            color = Box_Border
                                        )
                                        .size(dimensionResource(id = R.dimen.word_box_size))
                                        .wrapContentHeight(Alignment.CenterVertically)
                                        .wrapContentWidth(Alignment.CenterHorizontally),
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = White
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}