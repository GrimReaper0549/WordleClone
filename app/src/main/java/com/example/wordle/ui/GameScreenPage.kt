package com.example.wordle.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.example.wordle.R
import com.example.wordle.data.WordleUiState
import com.example.wordle.ui.theme.Black
import com.example.wordle.ui.theme.Box_Border
import com.example.wordle.ui.theme.Gray
import com.example.wordle.ui.theme.Green
import com.example.wordle.ui.theme.Keyboard_Letters_Background_Color
import com.example.wordle.ui.theme.LightColorScheme
import com.example.wordle.ui.theme.Transparent
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

@Composable
private fun WordInBlock(word: List<Char>){
    for( i in word.indices) {
        val backgroundColor = letterColorMap.getValue(0)
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
                    color = when (backgroundColor == letterColorMap.getValue(0)) {
                        true -> Box_Border
                        else -> backgroundColor
                    }
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
private fun KeyboardLettersInBlock(word: List<Char>, onKeyboardKeyClick: (Char)-> Unit){
    for( i in word.indices) {
        Button(onClick = { onKeyboardKeyClick(word[i]) },
            shape = RoundedCornerShape(dimensionResource(id =R.dimen.keyboard_box_rounded_corner_radius)),
            elevation = ButtonDefaults.elevatedButtonElevation(defaultElevation = dimensionResource(id = R.dimen.how_to_card_elevation)),
            modifier = Modifier
                .padding(
                    start = dimensionResource(id = R.dimen.extra_extra_small_padding),
                    end = dimensionResource(id = R.dimen.extra_extra_small_padding),
                    bottom = dimensionResource(id = R.dimen.extra_small_padding)
                )
                .wrapContentHeight(Alignment.CenterVertically)
                .wrapContentWidth(Alignment.CenterHorizontally)
                .height(dimensionResource(id = R.dimen.keyboard_box_height))
                .width(dimensionResource(id = when(word[i]){
                    '1','2'-> R.dimen.keyboard_box_width_enter_and_backspace_keys
                    else-> R.dimen.keyboard_box_width
                })),
            contentPadding = PaddingValues(dimensionResource(id = R.dimen.no_padding)),
            colors = ButtonDefaults.buttonColors(containerColor = Keyboard_Letters_Background_Color)
        ) {
            when(word[i]){
                '2'->
                    Icon(painter = painterResource(id = R.drawable.backspace), contentDescription = stringResource(
                        id = R.string.back_space),
                        tint = White)
                '1'->
                    Text(
                        text = "ENTER",
                        style = MaterialTheme.typography.bodySmall,
                        color = White
                    )
                else->
                    Text(
                        text = "${word[i]}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = White
                    )
            }
        }
    }
}


@Composable
fun GamePage(
    uiState: WordleUiState,
    onKeyboardKeyClick: (Char) -> Unit
) {
    Scaffold(
        topBar = { WordleTopAppBar() }
    ) { innerPadding ->
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)){
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(
                    modifier = Modifier
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
                Spacer(modifier = Modifier.weight(1F))
                Column(horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()) {
                    repeat(3) {
                        Row {
                            KeyboardLettersInBlock(word = KEYBOARD_ROWS[it].toList(), onKeyboardKeyClick = onKeyboardKeyClick)
                        }
                    }
                }
            }
        }
    }
}