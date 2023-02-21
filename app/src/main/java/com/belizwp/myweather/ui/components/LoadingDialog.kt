package com.belizwp.myweather.ui.components

import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import com.belizwp.myweather.ui.theme.MyWeatherTheme

@Composable
fun LoadingDialog(modifier: Modifier = Modifier) {
    Dialog(
        onDismissRequest = {},
    ) {
        CircularProgressIndicator(modifier = modifier)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DialogBoxLoadingPreview() {
    MyWeatherTheme {
        LoadingDialog()
    }
}
