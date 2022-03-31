package nolambda.stream.cleaningservice.plugin.components

import io.kotest.core.spec.Spec
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import nolambda.stream.cleaningservice.remover.DeleteActionRemover
import nolambda.stream.cleaningservice.report.DefaultReportParser
import nolambda.stream.cleaningservice.utils.SimpleLogger
import java.io.File

class CleaningFromReportSpec : StringSpec({

    val sampleDir = File(projectRootDir(), "sample/")
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

        val removedFiles = listOf(
            "myawesomemodule1/src/main/res/values/strings-unused.xml",
            "myawesomemodule2/src/main/res/layout/acitivty_unused.xml"
        )

        val removedItemFiles = listOf(
            "myawesomemodule1/src/main/res/values/strings.xml"
        )

        removedFiles.forEach {
            File(sampleDir, it).exists() shouldBe false
        }

        removedItemFiles.forEach {
            File(sampleDir, it).exists() shouldBe true
        }
    }
}) {
    override fun afterSpec(spec: Spec) {
        // Remove changes to sample/
        Command("git checkout -- sample/").runCommand(projectRootDirFile())
    }
}
