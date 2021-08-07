package io.obez.kmenu

import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.io.BufferedReader
import java.io.InputStreamReader

@DelicateCoroutinesApi
object CommandRunner {
    fun run(command: String): StateFlow<String> {
        val process = ProcessBuilder("bash", "-c", command).start()
//        val process: Process = Runtime.getRuntime().exec(arrayOf("bash", "-c", command))

        val stdInput = BufferedReader(InputStreamReader(process.inputStream))
        val stdError = BufferedReader(InputStreamReader(process.errorStream))

        val flow = MutableStateFlow("")

        var output: String? = null
        GlobalScope.launch(context = Dispatchers.IO, block = {
            output = stdInput.readLine()
            while(output != null) {
                output?.let { flow.value = it }
                println(output)
                output = stdInput.readLine()
            }

            output = stdError.readLine()
            while (output != null) {
                println(output)
                output = stdError.readLine()
            }
        })

        return flow
    }
}