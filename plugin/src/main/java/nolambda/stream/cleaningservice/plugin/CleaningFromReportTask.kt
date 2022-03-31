package nolambda.stream.cleaningservice.plugin

import nolambda.stream.cleaningservice.remover.DeleteActionRemover
import nolambda.stream.cleaningservice.report.DefaultReportParser
import nolambda.stream.cleaningservice.utils.NoopLogger
import nolambda.stream.cleaningservice.utils.SimpleLogger
import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import java.io.File

abstract class CleaningFromReportTask : DefaultTask() {

    @get:Input
    abstract val extension: Property<CleaningServicePluginExtension>

    @get:Input
    abstract val reportDir: DirectoryProperty

    @TaskAction
    fun doClean() {
        val parser = DefaultReportParser(
            projectDir = project.projectDir,
            reportDir = reportDir.get().asFile
        )

        val enableLog = extension.get().enableLog.getOrElse(true)
        val logger = if (enableLog) SimpleLogger() else NoopLogger

        val actions = parser.parse()
        val remover = DeleteActionRemover(logger = logger)

        remover.remove(actions)
    }
}
