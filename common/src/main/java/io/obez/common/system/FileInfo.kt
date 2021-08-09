package io.obez.common.system

data class FileInfo(
    val path: String,
    val name: String
)

data class DesktopFileInfo(
    val path: String,
    val name: String,
    val programName: String
)