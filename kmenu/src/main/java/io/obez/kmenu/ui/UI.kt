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
                        backgroundColor = MaterialTheme.colors.background
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
                        text = item
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
                        text = item
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
        Icon(onClick = {  }, iconPath = "nautilus.svg")
        Icon(onClick = {  }, iconPath = "firefox.svg")
        Icon(onClick = {  }, iconPath = "terminal.svg")
        Icon(onClick = {  }, iconPath = "atom.svg")
        Icon(onClick = {  }, iconPath = "androidstudio.svg")
        Icon(onClick = {  }, iconPath = "gimp.svg")
        Icon(onClick = {  }, iconPath = "spotify.svg")
        Icon(onClick = {  }, iconPath = "steam.svg")
        Icon(onClick = {  }, iconPath = "slack.svg")
        Icon(onClick = {  }, iconPath = "arch.svg")
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