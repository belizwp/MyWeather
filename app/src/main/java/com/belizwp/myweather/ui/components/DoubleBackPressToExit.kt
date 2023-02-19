package com.belizwp.myweather.ui.components

import android.app.Activity
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun DoubleBackPressToExit() {
    var pressedOnce by remember { mutableStateOf(false) }
    var resetPressedJob by remember { mutableStateOf<Job?>(null) }
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    BackHandler {
        val activity = (context as? Activity)
        if (pressedOnce) {
            resetPressedJob?.cancel()
            activity?.finish()
        } else {
            Toast.makeText(context, "Press BACK again to exit", Toast.LENGTH_SHORT).show()
            pressedOnce = true
            resetPressedJob = coroutineScope.launch {
                delay(2000)
                pressedOnce = false
            }
        }
    }
}
