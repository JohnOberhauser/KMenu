package io.obez.kmenu.utils

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.input.key.key
import io.obez.common.system.CommandRunner
import io.obez.common.system.Commands
import io.obez.kmenu.model.SelectedGroup
import io.obez.kmenu.viewModel.viewModel

@ExperimentalComposeUiApi
fun KeyEvent.handle() {
    when(key) {
        Key.Escape -> {
            Commands.close()
        }
        Key.Enter -> {
            if (viewModel.selectedGroup.value == SelectedGroup.Apps) {
                viewModel.selectedDesktopApp.value?.let {
                    Commands.gtkLaunch(it)
                }
            } else {
                viewModel.selectedBinary.value?.let {
                    CommandRunner.run(it)
                    Commands.close()
                }
            }
        }
        Key.DirectionDown -> {
            viewModel.onKeyDown()
        }
        Key.DirectionUp -> {
            viewModel.onKeyUp()
        }
        Key.Tab -> {
            viewModel.toggleSelectedGroup()
        }
    }
}