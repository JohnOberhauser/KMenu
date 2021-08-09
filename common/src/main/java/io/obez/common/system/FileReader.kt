package io.obez.common.system

import java.io.File
import java.util.*

object FileReader {
    fun readFile(file: File): String {
        val scanner = Scanner(file)
        var contents = ""
        while (scanner.hasNextLine()) {
            contents += scanner.nextLine()
            contents += "\n"
        }
        scanner.close()
        return contents
    }

    fun getNameFromDesktopFile(path: String): String? {

        val file = File(path)
        val contents = readFile(file)
        contents.split("\n").forEach {
            if (it.startsWith("Name=")) {
                return it.substringAfter("Name=")
            }
        }
        return null
    }
}