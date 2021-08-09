package io.obez.kpower.ui

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
import io.obez.common.system.Commands

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
                Icon(onClick = { Commands.lock() }, iconPath = "lock.svg")
                Icon(onClick = { Commands.sleep() }, iconPath = "pause.svg")
                Icon(onClick = { Commands.logout() }, iconPath = "logout.svg")
                Icon(onClick = { Commands.restart() }, iconPath = "reload.svg")
                Icon(onClick = { Commands.shutdown() }, iconPath = "power.svg")
                Icon(onClick = { Commands.close() }, iconPath = "close.svg")
            }
        }
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
                .padding(all = 50.dp)
                .size(75.dp),
            painter = svgResource(iconPath),
            tint = MaterialTheme.colors.primary,
            contentDescription = ""
        )
    }
}
