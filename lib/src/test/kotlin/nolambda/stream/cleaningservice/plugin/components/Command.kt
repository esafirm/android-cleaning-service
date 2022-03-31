package nolambda.stream.cleaningservice.plugin.components

import java.io.File
import java.util.concurrent.TimeUnit

class Command(private val stringCommand: String) {
    fun runCommand(workingDir: File? = null) {

        val dir = workingDir ?: File(System.getProperty("user.dir"))
        val commandArray = stringCommand.split(" ").toTypedArray()

        println("Running command: $stringCommand in ${dir.path}")

        ProcessBuilder(*commandArray)
            .directory(dir)
            .redirectOutput(ProcessBuilder.Redirect.INHERIT)
            .redirectError(ProcessBuilder.Redirect.INHERIT)
            .start()
            .waitFor(60, TimeUnit.MINUTES)
    }
}
