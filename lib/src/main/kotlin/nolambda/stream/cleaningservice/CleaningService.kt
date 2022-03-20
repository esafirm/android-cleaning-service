package nolambda.stream.cleaningservice

import nolambda.stream.cleaningservice.remover.file.DrawableFileRemover
import nolambda.stream.cleaningservice.remover.file.LayoutFileRemover
import nolambda.stream.cleaningservice.remover.xml.StringXmlRemover
import java.io.File

fun main() {
    val extension = CleaningServiceConfig(dryRun = false)
    val removers = listOf(
        DrawableFileRemover(),
        LayoutFileRemover(),
        StringXmlRemover()
    )

    val modules = getSampleModules()
    removers.map { it.remove(modules, modules, extension) }
}

private fun getSampleModules(): List<String> {
    val currentDirPath = System.getProperty("user.dir")
    val sampleDir = File(currentDirPath, "sample/")

    val modules = listOf(1, 2, 3)
        .map { "myawesomemodule$it" }
        .map { File(sampleDir, it) }

    val appModule = File(sampleDir, "app/")

    val allModules = modules + appModule

    return allModules.map { it.path }.also {
        println("Scanning in ${it.joinToString("\n")}")
    }
}
