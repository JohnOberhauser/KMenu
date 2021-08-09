package io.obez.kmenu.viewModel

import io.obez.common.system.Search
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ViewModel {

    val viewModelScope = CoroutineScope(Dispatchers.Default)

    private val _desktopApps = MutableStateFlow(listOf<String>())
    val desktopApps: StateFlow<List<String>> = _desktopApps
    private val allDesktopApps: List<String>

    val searchText = MutableStateFlow("")

    private val _pathBinaries = MutableStateFlow(listOf<String>())
    val pathBinaries: StateFlow<List<String>> = _pathBinaries
    private val allPathBinaries: List<String>

    init {
        allDesktopApps = findDesktopApps()
        _desktopApps.value = allDesktopApps

        allPathBinaries = findPathBinaries()
        _pathBinaries.value = allPathBinaries

        viewModelScope.launch {
            searchText.collect {
                search()
            }
        }
    }

    private fun findDesktopApps(): List<String> {
        val files = Search.searchDirectories(
            directoryPaths = listOf(
                "${System.getenv("HOME")}/.local/share/applications",
                "/usr/share/applications",
                "/var/lib/flatpak/app"
            ),
            searchString = ".desktop"
        )
        return files.map { it.name.substringBefore(".desktop") }.sorted()
    }

    private fun findPathBinaries(): List<String> {
        val files = Search.searchDirectories(
            directoryPaths = System.getenv("PATH").split(":"),
            searchString = ""
        )
        return files.map { it.name }.sorted()
    }

    private fun search() {
        _desktopApps.value = allDesktopApps.filter { it.contains(searchText.value) }
        _pathBinaries.value = allPathBinaries.filter { it.contains(searchText.value) }
    }
}