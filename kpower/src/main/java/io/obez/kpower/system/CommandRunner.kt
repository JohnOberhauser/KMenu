package io.obez.kpower.system

object CommandRunner {
    fun run(command: String): Process = ProcessBuilder("bash", "-c", command).start()
}