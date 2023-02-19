package com.belizwp.myweather.ui.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun RoundedButton(
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(50),
        modifier = modifier
    ) {
        content()
    }
}
