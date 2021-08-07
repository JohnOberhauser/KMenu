package io.obez.kmenu

import androidx.compose.desktop.DesktopMaterialTheme
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

@Composable
fun App() {
    var text by remember { mutableStateOf("Hello, World!") }

    DesktopMaterialTheme {
        Button(onClick = {
            text = "Hello, Desktop!"
        }) {
            Text(text)
        }
    }
}

@ExperimentalComposeUiApi
fun main() = application {
//    CommandRunner.run("swaylock -i /ssd/home/workspace/Varda-Theme/wallpaper/varda.jpg" +
//            " --indicator-idle-visible --font \"JetBrainsMono Patched Glyphs\"" +
//            " --indicator-thickness 5" +
//            " --indicator-radius 100" +
//            " --key-hl-color 257B76" +
//            " --inside-color 0C0E11" +
//            " --inside-ver-color 0C0E11" +
//            " --inside-wrong-color 0C0E11" +
//            " --ring-color 52677C" +
//            " --ring-ver-color 257B76" +
//            " --ring-wrong-color 733447" +
//            " --separator-color 0C0E11" +
//            " --text-color D0EBEE" +
//            " --text-ver-color D0EBEE" +
//            " --text-wrong-color D0EBEE" +
//            " -n")
    CommandRunner.run("ls /usr/bin/swaylock")
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}