package io.obez.common.system

import java.io.File

object Search {
    fun searchDirectories(directoryPaths: List<String>, searchString: String): List<FileInfo> {
        val files = mutableListOf<FileInfo>()
        directoryPaths.forEach {
            files.addAll(searchDirectory(it, searchString))
        }
        return files
    }

    fun searchDirectory(directoryPath: String, searchString: String): List<FileInfo> {
        val file = File(directoryPath)
        return if (file.isDirectory) {
            search(file, searchString)
        } else {
            listOf()
        }
    }

    private fun search(file: File, searchString: String): List<FileInfo> {
        val files = mutableListOf<FileInfo>()
        if (file.canRead()) {
            file.listFiles()?.let { fileList ->
                for (temp in fileList) {
                    if (temp.isDirectory) {
                        files.addAll(search(temp, searchString))
                    } else {
                        if (temp.name.toLowerCase().contains(searchString)) {
                            files.add(
                                FileInfo(
                                    temp.absolutePath,
                                    temp.name
                                )
                            )
                        }
                    }
                }
            }
        }

        return files
    }
}