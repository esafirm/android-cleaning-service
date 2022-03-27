package nolambda.stream.cleaningservice.plugin.components

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import nolambda.stream.cleaningservice.CleaningServiceConfig
import nolambda.stream.cleaningservice.remover.file.DrawableFileRemover
import nolambda.stream.cleaningservice.remover.file.LayoutFileRemover
import nolambda.stream.cleaningservice.remover.xml.StringXmlRemover
import nolambda.stream.cleaningservice.report.DefaultReportEngineFactory
import java.io.File

class CleaningServiceSpek : StringSpec({

    val currentDirPath = File(System.getProperty("user.dir"))
    val sampleDir = File(currentDirPath.parent, "sample/")

    val cleaningServiceResultDir = File(sampleDir, DefaultReportEngineFactory.DEFAULT_DIR_NAME)
    val targetFile = File(sampleDir, "build/target_file")

    // Clean up existing results
    cleaningServiceResultDir.deleteRecursively()
    targetFile.delete()

    val modules = listOf(1, 2, 3)
        .map { "myawesomemodule$it" }
        .map { File(sampleDir, it) }

    val appModule = File(sampleDir, "app/")

    val allModules = modules + appModule

    val sampleModules = allModules.map { it.path }.also {
        println("Scanning in ${it.joinToString("\n")}")
    }

    "Sample scenario should run correctly" {
        val extension = CleaningServiceConfig(dryRun = false)
        val removers = listOf(
            DrawableFileRemover(),
            LayoutFileRemover(),
            StringXmlRemover()
        )

        removers.map { it.remove(sampleModules, sampleModules, extension) }

        // Check if report file is generated
//        val generated = cleaningServiceResultDir.list().orEmpty()
//        generated.size shouldBe 2

        // Check unused file is deleted
        val unusedFile = File(sampleDir, "myawesomemodule2/src/main/res/layout/acitivty_unused.xml")
        unusedFile.exists() shouldBe false
    }
})
