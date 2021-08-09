package io.obez.kmenu.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.svgResource
import androidx.compose.ui.unit.dp
import io.obez.common.system.CommandRunner
import io.obez.common.system.Commands
import io.obez.kmenu.viewModel.ViewModel

val viewModel = ViewModel()

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
                TextField(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(20.dp)
                        .width(500.dp)
                        .focusRequester(searchFocusRequester),
                    value = viewModel.searchText.collectAsState().value,
                    onValueChange = { viewModel.searchText.value = it },
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
                SearchResults()
                MainApps()
            }
        }
    }
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
        Box(
            modifier = Modifier
                .width(500.dp)
                .height(200.dp)
                .border(color = MaterialTheme.colors.secondary, width = 1.dp)

        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp)
            ) {
                items(
                    desktopApps
                ) { item ->
                    Text(
                        text = item.programName,
                        color = MaterialTheme.colors.primary
                    )
                }
            }
        }
        Spacer(
            modifier = Modifier
                .width(100.dp)
        )
        Box(
            modifier = Modifier
                .width(500.dp)
                .height(200.dp)
                .border(color = MaterialTheme.colors.secondary, width = 1.dp)

        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp)
            ) {
                items(
                    systemPrograms
                ) { item ->
                    Text(
                        text = item,
                        color = MaterialTheme.colors.primary
                    )
                }
            }
        }
    }
}

@Composable
fun MainApps() {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.BottomCenter)
    ) {
        Icon(appName = "Files", iconPath = "nautilus.svg")
        Icon(appName = "Firefox", iconPath = "firefox.svg")
        Icon(appName = "Terminator", iconPath = "terminal.svg")
        Icon(appName = "Atom", iconPath = "atom.svg")
        Icon(appName = "Android Studio", iconPath = "androidstudio.svg")
        Icon(appName = "GNU Image Manipulation Program", iconPath = "gimp.svg")
        Icon(appName = "Spotify", iconPath = "spotify.svg")
        Icon(appName = "Steam", iconPath = "steam.svg")
        Icon(appName = "Slack", iconPath = "slack.svg")
        Icon(appName = "Add/Remove Software", iconPath = "arch.svg")
    }
}

@Composable
fun Icon(appName: String, iconPath: String) {
    Surface(
        modifier = Modifier
            .clip(CircleShape)
            .clickable { viewModel.getDesktopFileName(appName)?.let { Commands.gtkLaunch(it) } }
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