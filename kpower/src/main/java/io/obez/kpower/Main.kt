package io.obez.kpower

import androidx.compose.material.MaterialTheme
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowSize
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import io.obez.kpower.theme.ColorPalette
import io.obez.kpower.ui.Root

@ExperimentalComposeUiApi
fun main() = application {
    Window(
        undecorated = true,
        resizable = false,
        onCloseRequest = ::exitApplication,
        title = "KPower - power options",
        alwaysOnTop = true,
        state = rememberWindowState(
            size = WindowSize(
                width = 1100.dp,
                height = 200.dp
            )
        )
    ) {
        MaterialTheme(
            colors = ColorPalette,
        ) {
            Root()
        }
    }
}