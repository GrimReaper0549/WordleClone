package com.example.wordle.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.wordle.R
import com.example.wordle.data.WordleUiState
import com.example.wordle.ui.theme.Black
import com.example.wordle.ui.theme.White
import com.example.wordle.ui.theme.WordleTheme
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun LandingPage(
    onPlayButtonClicked: ()->Unit = {},
    onHowToPlayClicked: ()->Unit = {}
){
    Box {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.background),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(painter = painterResource(id = R.drawable.wordle_logo), contentDescription = null)
            Text(
                text = stringResource(id = R.string.app_name),
                style = MaterialTheme.typography.displayMedium,
                color = MaterialTheme.colorScheme.onPrimary
            )
            Text(
                text = stringResource(id = R.string.landing_guess_in_6),
                modifier = Modifier.width(dimensionResource(id = R.dimen.guess_width)),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onPrimary
            )

            Column(modifier = Modifier.padding(top = dimensionResource(id = R.dimen.large_padding))) {
                OutlinedButton(
                    onClick = { onHowToPlayClicked() },
                    modifier = Modifier
                        .width(dimensionResource(id = R.dimen.button_width))
                        .padding(top = dimensionResource(id = R.dimen.small_padding)),
                ) {
                    Text(text = stringResource(id = R.string.how_to_play))
                }
                OutlinedButton(
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .width(dimensionResource(id = R.dimen.button_width))
                        .padding(top = dimensionResource(id = R.dimen.extra_small_padding)),
                ) {
                    Text(text = stringResource(id = R.string.login))
                }
                Button(
                    onClick = { onPlayButtonClicked() },
                    modifier = Modifier
                        .width(dimensionResource(id = R.dimen.button_width))
                        .padding(top = dimensionResource(id = R.dimen.extra_small_padding)),
                    colors = ButtonDefaults.buttonColors(
                        contentColor = White,
                        containerColor = Black
                    )
                ) {
                    Text(text = stringResource(id = R.string.play))
                }
            }
            val currentDate = LocalDate.now()
            val formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy")
            val formattedDate = currentDate.format(formatter)

            Text(
                text = formattedDate,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = dimensionResource(id = R.dimen.large_padding)),
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}

