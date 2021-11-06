package nolambda.stream.cleaningservice

import nolambda.stream.cleaningservice.components.file.DrawableFileRemover
import nolambda.stream.cleaningservice.components.xml.StringXmlRemover

fun main() {
    val extension = CleaningServiceConfig()
    val removers = listOf(
        DrawableFileRemover(),
        StringXmlRemover()
    )

    val moduleSrcDirs = listOf(
        "/Users/esafirman/Documents/OSS/architecture-components-samples/MADSkillsNavigationSample/app/"
    )

    removers.map { it.remove(moduleSrcDirs, extension) }
}