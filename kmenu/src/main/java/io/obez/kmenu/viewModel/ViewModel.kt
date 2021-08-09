package io.obez.kmenu.viewModel

import io.obez.common.system.DesktopFileInfo
import io.obez.common.system.FileReader
import io.obez.common.system.Search
import io.obez.kmenu.model.SelectedGroup
import io.obez.kmenu.utils.appIgnoreList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

val viewModel = ViewModel()

class ViewModel {

    private val viewModelScope = CoroutineScope(Dispatchers.Default)

    val searchText = MutableStateFlow("")

    private val _desktopApps = MutableStateFlow(listOf<DesktopFileInfo>())
    val desktopApps: StateFlow<List<DesktopFileInfo>> = _desktopApps
    private var allDesktopApps: List<DesktopFileInfo> = listOf()
    val selectedDesktopApp: MutableStateFlow<DesktopFileInfo?> = MutableStateFlow(null)

    private val _pathBinaries = MutableStateFlow(listOf<String>())
    val pathBinaries: StateFlow<List<String>> = _pathBinaries
    private var allPathBinaries: List<String> = listOf()
    val selectedBinary: MutableStateFlow<String?> = MutableStateFlow(null)

    val selectedGroup = MutableStateFlow(SelectedGroup.Apps)

    var needsScroll = false

    init {
        allDesktopApps = findDesktopApps()
        _desktopApps.value = allDesktopApps
        selectedDesktopApp.value = allDesktopApps.firstOrNull()

        allPathBinaries = findPathBinaries()
        _pathBinaries.value = allPathBinaries

        viewModelScope.launch {
            searchText.collect {
                search()
            }
        }

        viewModelScope.launch {
            selectedGroup.collect {
                if (it == SelectedGroup.Apps) {
                    if (selectedDesktopApp.value == null) {
                        selectedDesktopApp.value = desktopApps.value.firstOrNull()
                    }
                    selectedBinary.value = null
                } else {
                    if (selectedBinary.value == null) {
                        selectedBinary.value = pathBinaries.value.firstOrNull()
                    }
                    selectedDesktopApp.value = null
                }
            }
        }
    }

    private fun findDesktopApps(): List<DesktopFileInfo> =
        Search.searchDirectories(
            directoryPaths = listOf(
                "${System.getenv("HOME")}/.local/share/applications",
                "/usr/share/applications",
                "/var/lib/flatpak/app"
            ),
            searchString = ".desktop",
            flatpakSearch = true
        ).filter {
            if (it.path.contains("/var/lib/flatpak/app")) {
                it.path.contains("current/active/files/share")
            } else {
                true
            }
        }.mapNotNull {
            FileReader.getNameFromDesktopFile(it.path)?.let { appName ->
                DesktopFileInfo(
                    it.path,
                    it.name,
                    appName
                )
            }
        }.filter {
            !appIgnoreList.contains(it.programName)
        }.sortedBy { it.programName }

    private fun findPathBinaries(): List<String> =
        Search.searchDirectories(
            directoryPaths = System.getenv("PATH").split(":"),
            searchString = ""
        ).map { it.name }.distinct().sorted()

    private fun search() {
        _desktopApps.value = allDesktopApps.filter { it.programName.contains(searchText.value, ignoreCase = true) }
        _pathBinaries.value = allPathBinaries.filter { it.contains(searchText.value, ignoreCase = true) }
        if (selectedGroup.value == SelectedGroup.Apps) {
            selectedDesktopApp.value = desktopApps.value.firstOrNull()
            if (selectedDesktopApp.value == null && pathBinaries.value.isNotEmpty()) {
                toggleSelectedGroup()
            }
        } else if (selectedGroup.value == SelectedGroup.Binaries) {
            selectedBinary.value = pathBinaries.value.firstOrNull()
            if (selectedBinary.value == null && desktopApps.value.isNotEmpty()) {
                toggleSelectedGroup()
            }
        }
    }

    fun getDesktopFileByName(appName: String): DesktopFileInfo? =
        allDesktopApps.find { it.programName.equals(appName, ignoreCase = true) }

    fun toggleSelectedGroup() {
        selectedGroup.value = if (selectedGroup.value == SelectedGroup.Apps) {
            SelectedGroup.Binaries
        } else {
            SelectedGroup.Apps
        }
    }

    fun onKeyDown() {
        moveSelectedIndex(1)
    }

    fun onKeyUp() {
        moveSelectedIndex(-1)
    }

    private fun moveSelectedIndex(by: Int) {
        if (selectedGroup.value == SelectedGroup.Apps) {
            desktopApps.value.getOrNull(desktopApps.value.indexOf(selectedDesktopApp.value) + by)?.let {
                selectedDesktopApp.value = it
            }
        } else {
            pathBinaries.value.getOrNull(pathBinaries.value.indexOf(selectedBinary.value) + by)?.let {
                selectedBinary.value = it
            }
        }
        needsScroll = true
    }
}