package nolambda.stream.cleaningservice

import nolambda.stream.cleaningservice.components.file.DrawableFileRemover
import nolambda.stream.cleaningservice.components.file.LayoutFileRemover
import nolambda.stream.cleaningservice.components.xml.StringXmlRemover
import java.io.File

fun main() {
    val extension = CleaningServiceConfig()
    val removers = listOf(
        DrawableFileRemover(),
        LayoutFileRemover()
    )

    val gojekModules = listOf(
        "/Users/esa.firman/Documents/gojek/GoHost/send/kilatrewrite",
        "/Users/esa.firman/Documents/gojek/GoHost/gojek",
        "/Users/esa.firman/Documents/gojek/GoHost/gofood/food"
    )

    removers.map { it.remove(gojekModules, extension) }
}

private fun getSampleModules(): List<String> {
    val currentDirPath = System.getProperty("user.dir")
    val sampleDir = File(currentDirPath, "sample/")

    val modules = listOf(1, 2, 3)
        .map { "myawesomemodule$it" }
        .map { File(sampleDir, it) }

    val appModule = File(currentDirPath, "app/")

    val allModules = modules + appModule

    return allModules.map { it.path }.also {
        println("Scanning in ${it.joinToString("\n")}")
    }
}
