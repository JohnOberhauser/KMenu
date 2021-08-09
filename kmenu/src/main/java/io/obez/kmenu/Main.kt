package io.obez.kmenu

import androidx.compose.material.MaterialTheme
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowSize
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import io.obez.common.system.Commands
import io.obez.common.theme.ColorPalette
import io.obez.kmenu.ui.Root
import io.obez.kmenu.ui.searchFocusRequester
import io.obez.kmenu.ui.typography
import io.obez.kmenu.utils.handle

@ExperimentalComposeUiApi
fun main() = application {
    Window(
        undecorated = true,
        resizable = false,
        onCloseRequest = ::exitApplication,
        title = "KMenu - app launcher",
        alwaysOnTop = true,
        state = rememberWindowState(
            size = WindowSize(
                width = 1200.dp,
                height = 400.dp
            )
        ),
        onKeyEvent = {
            it.handle()
            searchFocusRequester.requestFocus()
            return@Window true
        }
    ) {
        MaterialTheme(
            colors = ColorPalette,
            typography = typography
        ) {
            Root()
        }
    }
}