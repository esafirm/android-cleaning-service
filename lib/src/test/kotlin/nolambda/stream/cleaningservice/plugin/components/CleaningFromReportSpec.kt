package nolambda.stream.cleaningservice.plugin.components

import io.kotest.core.spec.style.StringSpec
import nolambda.stream.cleaningservice.remover.DeleteActionRemover
import nolambda.stream.cleaningservice.report.DefaultReportParser
import nolambda.stream.cleaningservice.utils.SimpleLogger
import java.io.File

class CleaningFromReportSpec : StringSpec({

    val currentDirPath = File(System.getProperty("user.dir"))
    val sampleDir = File(currentDirPath.parent, "sample/")
    val reportDir = File(sampleDir, "build/cleaning/")

    val parser = DefaultReportParser(
        projectDir = sampleDir,
        reportDir = reportDir
    )

    val actions = parser.parse()
    val remover = DeleteActionRemover(
        logger = SimpleLogger()
    )

    "Scenario must run successfully" {
        remover.remove(actions)
    }
})
