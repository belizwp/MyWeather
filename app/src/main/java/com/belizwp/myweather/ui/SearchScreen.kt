package com.belizwp.myweather.ui

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.belizwp.myweather.data.SearchUiState
import com.belizwp.myweather.ui.components.RoundedButton
import com.belizwp.myweather.ui.theme.MyWeatherTheme
import kotlinx.coroutines.delay

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchScreen(
    uiState: SearchUiState,
    modifier: Modifier = Modifier,
    onQueryChanged: (String) -> Unit = {},
    onSearchButtonClicked: () -> Unit = {},
) {
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }
    val keyboard = LocalSoftwareKeyboardController.current

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
        delay(100)
        keyboard?.show()
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        focusManager.clearFocus()
                    }
                )
            },
    ) {
        Column {
            OutlinedTextField(
                value = uiState.query,
                onValueChange = onQueryChanged,
                singleLine = true,
                label = { Text("Enter City Name") },
                isError = !uiState.isQueryValid,
                trailingIcon = {
                    if (!uiState.isQueryValid) {
                        Icon(Icons.Filled.Error, "error", tint = MaterialTheme.colors.error)
                    }
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(onSearch = { onSearchButtonClicked() }),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .focusRequester(focusRequester)
            )
            Text(
                text = "City Name Cannot Be Empty",
                color = MaterialTheme.colors.error,
                style = MaterialTheme.typography.caption,
                modifier = Modifier
                    .padding(start = 16.dp)
                    .alpha(if (!uiState.isQueryValid) 1f else 0f)
            )
        }
        RoundedButton(
            onClick = onSearchButtonClicked,
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "Find")
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SearchScreenPreview() {
    MyWeatherTheme {
        SearchScreen(SearchUiState())
    }
}
