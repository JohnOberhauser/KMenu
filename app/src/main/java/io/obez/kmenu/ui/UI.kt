package io.obez.kmenu.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.svgResource
import androidx.compose.ui.unit.dp
import io.obez.kmenu.Commands

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
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center)
            ) {
                Lock()
                Sleep()
                Logout()
                Restart()
                Shutdown()
            }
        }
    }
}

@Composable
fun Lock() {
    Surface(
        modifier = Modifier
            .clip(CircleShape)
            .clickable { Commands.lock() }
    ) {
        Icon(
            modifier = Modifier
                .padding(all = 50.dp),
            painter = svgResource("lock.svg"),
            tint = MaterialTheme.colors.primary,
            contentDescription = ""
        )
    }

}

@Composable
fun Sleep() {
    Surface(
        modifier = Modifier
            .clip(CircleShape)
            .clickable { Commands.sleep() }
    ) {
        Icon(
            modifier = Modifier
                .padding(all = 50.dp),
            painter = svgResource("pause.svg"),
            tint = MaterialTheme.colors.primary,
            contentDescription = ""
        )
    }
}

@Composable
fun Logout() {
    Surface(
        modifier = Modifier
            .clip(CircleShape)
            .clickable { Commands.logout() }
    ) {
        Icon(
            modifier = Modifier
                .padding(all = 50.dp),
            painter = svgResource("logout.svg"),
            tint = MaterialTheme.colors.primary,
            contentDescription = ""
        )
    }
}

@Composable
fun Restart() {
    Surface(
        modifier = Modifier
            .clip(CircleShape)
            .clickable { Commands.restart() }
    ) {
        Icon(
            modifier = Modifier
                .padding(all = 50.dp),
            painter = svgResource("reload.svg"),
            tint = MaterialTheme.colors.primary,
            contentDescription = ""
        )
    }
}

@Composable
fun Shutdown() {
    Surface(
        modifier = Modifier
            .clip(CircleShape)
            .clickable { Commands.shutdown() }
    ) {
        Icon(
            modifier = Modifier
                .padding(all = 50.dp),
            painter = svgResource("power.svg"),
            tint = MaterialTheme.colors.primary,
            contentDescription = ""
        )
    }
}