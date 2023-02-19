package com.belizwp.myweather.ui.components

import android.app.Activity
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun DoubleBackPressToExit() {
    var pressBackOnce by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    BackHandler {
        val activity = (context as? Activity)
        if (pressBackOnce) {
            activity?.finish()
        } else {
            Toast.makeText(context, "Press BACK again to exit", Toast.LENGTH_SHORT).show()
            pressBackOnce = true
            coroutineScope.launch {
                delay(2000)
                pressBackOnce = false
            }
        }
    }
}
