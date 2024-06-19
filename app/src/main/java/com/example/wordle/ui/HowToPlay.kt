package com.example.wordle.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.wordle.R
import com.example.wordle.ui.theme.Black
import com.example.wordle.ui.theme.Box_Border
import com.example.wordle.ui.theme.Gray
import com.example.wordle.ui.theme.Green
import com.example.wordle.ui.theme.LightColorScheme
import com.example.wordle.ui.theme.White
import com.example.wordle.ui.theme.Yellow

private val ruleList = listOf(R.string.rule_1, R.string.rule_2)
private val examples = listOf(
    mapOf(
        "word" to R.string.example_1_word,
        "description" to R.string.example_1_description,
        "value" to R.string.example_1_value
    ),
    mapOf(
        "word" to R.string.example_2_word,
        "description" to R.string.example_2_description,
        "value" to R.string.example_2_value
    ),
    mapOf(
        "word" to R.string.example_3_word,
        "description" to R.string.example_3_description,
        "value" to R.string.example_3_value
    )
)

private val colorMap = mapOf(
    0 to Color.Transparent,
    1 to Gray,
    2 to Yellow,
    3 to Green
)

@Composable
private fun WordInBlock(word: List<Char>, example: Map<String, Int>){
    for( i in word.indices) {
        val backgroundColor = colorMap.getValue(stringResource(example.getValue("value"))[i]-'0')
        Text(
            text = "${word[i]}",
            modifier = Modifier
                .padding(end = dimensionResource(id = R.dimen.small_padding))
                .background(color = backgroundColor)
                .border(
                    shape = RectangleShape,
                    width = dimensionResource(id = R.dimen.box_border_width),
                    color = Box_Border
                )
                .size(dimensionResource(id = R.dimen.box_size))
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
private fun Examples(){
    val context = LocalContext.current
    examples.forEach{ example ->
        Column(modifier = Modifier.padding(start = dimensionResource(id = R.dimen.no_padding), top = dimensionResource(id = R.dimen.small_padding), bottom = dimensionResource(id = R.dimen.small_padding))) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center, modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.extra_small_padding))){
                val word = context.getText(example["word"] ?: error("Word Not Found")).toList()
                WordInBlock(word = word, example = example)
            }
            Text(
                text = context.getText(example["description"] ?: error("Description Not Found")).toString(),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HowToPlayTopAppBar(
    navigateUp: ()-> Unit
){
    CenterAlignedTopAppBar(title = {
        Column(verticalArrangement = Arrangement.Center) {
            IconButton(onClick = { navigateUp() }) {
                Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null)
            }
            Divider(
                thickness = 1.5.dp,
                color = MaterialTheme.colorScheme.outline
            )
        }
    })
}

@Composable
fun HowToPlayCard(
    onPlayButtonClicked: ()-> Unit,
    navigateUp: () -> Unit
){
    Scaffold(
        topBar = { HowToPlayTopAppBar(
            navigateUp = { navigateUp() }
        ) }
    ) {
        innerPadding->
        Column {
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(start = dimensionResource(id = R.dimen.medium_padding))
            ) {
                Text(
                    text = stringResource(id = R.string.how_to_play),
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(
                        top = dimensionResource(id = R.dimen.small_padding),
                        bottom = dimensionResource(id = R.dimen.extra_small_padding)
                    )
                )
                Text(
                    text = stringResource(id = R.string.guess_in_6),
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(
                        bottom = dimensionResource(id = R.dimen.medium_padding)
                    )
                )
                val bulletColor = MaterialTheme.colorScheme.onPrimary
                Column {
                    ruleList.forEach {
                        Row(
                            modifier = Modifier.padding(dimensionResource(id = R.dimen.extra_small_padding)),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Canvas(
                                modifier = Modifier
                                    .padding(
                                        end = dimensionResource(id = R.dimen.bulletpoint_padding)
                                    )
                                    .size(dimensionResource(id = R.dimen.bulletpoint_size))
                            ) {
                                drawCircle(color = bulletColor)
                                //                        Make Bullet Point Color Dynamic to onPrimary
                            }
                            Text(
                                text = stringResource(id = it),
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }
                Text(
                    text = stringResource(id = R.string.examples), modifier = Modifier.padding(
                        top = dimensionResource(id = R.dimen.medium_padding)
                    ), style = MaterialTheme.typography.titleMedium
                )
                Examples()
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Spacer(modifier = Modifier.weight(1F))
                Row {
                    Spacer(modifier = Modifier.weight(1F))
                    Text(
                        text = stringResource(id = R.string.midnight), modifier = Modifier
                            .width(dimensionResource(id = R.dimen.midnight_line_width))
                            .padding(
                                top = dimensionResource(id = R.dimen.extra_large_padding),
                                bottom = dimensionResource(id = R.dimen.small_padding)
                            ),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.weight(1F))
                }
                Spacer(modifier = Modifier.weight(1F))
                Button(
                    onClick = { onPlayButtonClicked() },
                    modifier = Modifier
                        .width(dimensionResource(id = R.dimen.how_to_play_play_button_width))
                        .padding(
                            top = dimensionResource(id = R.dimen.large_padding),
                            bottom = dimensionResource(id = R.dimen.extra_large_padding)
                        ),
                    colors = ButtonDefaults.buttonColors(
                        contentColor = White,
                        containerColor = Black
                    )
                ) {
                    Text(
                        text = stringResource(id = R.string.play),
                        style = MaterialTheme.typography.headlineMedium
                    )
                }
            }
        }
    }
}
