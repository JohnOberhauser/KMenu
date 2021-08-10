package io.obez.kmenu.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.key.*
import androidx.compose.ui.input.pointer.pointerMoveFilter
import androidx.compose.ui.res.svgResource
import androidx.compose.ui.unit.dp
import io.obez.common.system.CommandRunner
import io.obez.common.system.Commands
import io.obez.kmenu.model.SelectedGroup
import io.obez.kmenu.utils.handle
import io.obez.kmenu.viewModel.viewModel
import kotlinx.coroutines.launch

@Composable
fun Root() {
    Surface(
        color = MaterialTheme.colors.background
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .border(color = MaterialTheme.colors.secondary, width = 1.dp)

        ) {
            Column {
                SearchBar()
                SearchResults()
                MainApps()
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ColumnScope.SearchBar() {
    TextField(
        modifier = Modifier
            .align(Alignment.CenterHorizontally)
            .padding(20.dp)
            .width(500.dp)
            .focusRequester(searchFocusRequester)
            .onPreviewKeyEvent {
                   when(it.key) {
                       Key.Tab,
                       Key.Enter,
                       Key.DirectionDown,
                       Key.DirectionUp -> {
                           if (it.type == KeyEventType.KeyDown) {
                               it.handle()
                           }
                           true
                       }
                       else -> false
                   }
            },
        value = viewModel.searchText.collectAsState().value,
        onValueChange = {
            viewModel.searchText.value = it
        },
        label = {
            Text(
                text = "Search"
            )
        },
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = MaterialTheme.colors.background,
            textColor = MaterialTheme.colors.primary,
            focusedLabelColor = MaterialTheme.colors.primary,
            unfocusedLabelColor = MaterialTheme.colors.primary,
            cursorColor = MaterialTheme.colors.primary,
            focusedIndicatorColor = MaterialTheme.colors.secondary,
            unfocusedIndicatorColor = MaterialTheme.colors.secondary
        )
    )
}

@Composable
fun SearchResults() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(170.dp)
            .wrapContentSize(Alignment.Center)
    ) {
        val desktopApps = viewModel.desktopApps.collectAsState().value
        val systemPrograms = viewModel.pathBinaries.collectAsState().value
        val selectedDesktopApp = viewModel.selectedDesktopApp.collectAsState().value
        val selectedBinary = viewModel.selectedBinary.collectAsState().value
        val selectedGroup = viewModel.selectedGroup.collectAsState().value

        val desktopListState = rememberLazyListState()
        val systemListState = rememberLazyListState()

        if (viewModel.needsScroll) {
            rememberCoroutineScope().launch {
                selectedDesktopApp?.let {
                    desktopListState.scrollToItem(desktopApps.indexOf(it))
                }
                selectedBinary?.let {
                    systemListState.scrollToItem(systemPrograms.indexOf(it))
                }
            }
            viewModel.needsScroll = false
        }

        SelectableColumn(
            label = "Apps",
            groupSelected = selectedGroup == SelectedGroup.Apps,
            listState = desktopListState
        ) {
            items(desktopApps) { item ->
                ListItem(
                    text = item.programName,
                    onClick = { Commands.gtkLaunch(item) },
                    onHover = {
                        if (viewModel.selectedGroup.value == SelectedGroup.Binaries) {
                            viewModel.selectedGroup.value = SelectedGroup.Apps
                        }
                        viewModel.selectedDesktopApp.value = item
                    },
                    isActive = item == selectedDesktopApp
                )
            }
        }
        Spacer(modifier = Modifier.width(100.dp))
        SelectableColumn(
            label = "Bins",
            groupSelected = selectedGroup == SelectedGroup.Binaries,
            listState = systemListState
        ) {
            items(systemPrograms) { item ->
                ListItem(
                    text = item,
                    onClick = { CommandRunner.run(item) },
                    onHover = {
                        if (viewModel.selectedGroup.value == SelectedGroup.Apps) {
                            viewModel.selectedGroup.value = SelectedGroup.Binaries
                        }
                        viewModel.selectedBinary.value = item
                    },
                    isActive = item == selectedBinary
                )
            }
        }
    }
}

@Composable
fun SelectableColumn(
    label: String,
    groupSelected: Boolean,
    listState: LazyListState,
    content: LazyListScope.() -> Unit
) {
    Column {
        Text(
            modifier = Modifier
                .padding(5.dp)
                .align(Alignment.CenterHorizontally),
            text = label,
            color = MaterialTheme.colors.primary
        )
        Box(
            modifier = Modifier
                .width(500.dp)
                .height(200.dp)
                .border(
                    color = if (groupSelected) MaterialTheme.colors.primary else MaterialTheme.colors.secondary,
                    width = 1.dp
                )
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 10.dp),
                state = listState
            ) {
                item { Spacer(modifier = Modifier.height(10.dp)) }
                content()
                item { Spacer(modifier = Modifier.height(10.dp)) }
            }
        }
    }
}

@Composable
fun ListItem(text: String, onClick: () -> Unit, onHover: () -> Unit, isActive: Boolean) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .pointerMoveFilter(
                onEnter = {
                    onHover()
                    false
                }
            )
            .background(color = if (isActive) MaterialTheme.colors.secondary else MaterialTheme.colors.background)
            .clickable { onClick() },
        text = text,
        color = MaterialTheme.colors.primary
    )
}

@Composable
fun MainApps() {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.BottomCenter)
    ) {
        Icon(onClick = { viewModel.getDesktopFileByName("Files")?.let { Commands.gtkLaunch(it) } }, iconPath = "nautilus.svg")
        Icon(onClick = { viewModel.getDesktopFileByName("Firefox")?.let { Commands.gtkLaunch(it) } }, iconPath = "firefox.svg")
        Icon(onClick = { viewModel.getDesktopFileByName("Terminator")?.let { Commands.gtkLaunch(it) } }, iconPath = "terminal.svg")
        Icon(onClick = { viewModel.getDesktopFileByName("Atom")?.let { Commands.gtkLaunch(it) } }, iconPath = "atom.svg")
        Icon(onClick = { viewModel.getDesktopFileByName("Android Studio")?.let { Commands.gtkLaunch(it) } }, iconPath = "androidstudio.svg")
        Icon(onClick = { viewModel.getDesktopFileByName("GNU Image Manipulation Program")?.let { Commands.gtkLaunch(it) } }, iconPath = "gimp.svg")
        Icon(
            onClick = {
                CommandRunner.run("terminator -e spt")
                Commands.close()
            },
            iconPath = "spotify.svg"
        )
        Icon(onClick = { viewModel.getDesktopFileByName("Steam")?.let { Commands.gtkLaunch(it) } }, iconPath = "steam.svg")
        Icon(onClick = { viewModel.getDesktopFileByName("Slack")?.let { Commands.gtkLaunch(it) } }, iconPath = "slack.svg")
        Icon(onClick = { viewModel.getDesktopFileByName("Add/Remove Software")?.let { Commands.gtkLaunch(it) } }, iconPath = "arch.svg")
    }
}

@Composable
fun Icon(onClick: () -> Unit, iconPath: String) {
    Surface(
        modifier = Modifier
            .clip(CircleShape)
            .clickable { onClick() }
    ) {
        Icon(
            modifier = Modifier
                .padding(all = 20.dp)
                .size(75.dp),
            painter = svgResource(iconPath),
            contentDescription = "",
            tint = MaterialTheme.colors.primary
        )
    }
}