package io.obez.kmenu.system

object CommandRunner {
    fun run(command: String): Process = ProcessBuilder("bash", "-c", command).start()
}