package nolambda.stream.cleaningservice.plugin

import nolambda.stream.cleaningservice.CleaningServiceConfig
import nolambda.stream.cleaningservice.remover.file.DrawableFileRemover
import nolambda.stream.cleaningservice.remover.file.LayoutFileRemover
import nolambda.stream.cleaningservice.plugin.utils.DependencyTracker
import nolambda.stream.cleaningservice.plugin.utils.ToStringLogger
import nolambda.stream.cleaningservice.plugin.utils.getDistributionDirectory
import org.gradle.api.DefaultTask
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

abstract class CleaningTask : DefaultTask() {

    companion object {
        private const val LOG_FILE_NAME = "cleaning_service_log.txt"
    }

    @get:Input
    abstract val config: Property<CleaningServiceConfig>

    @TaskAction
    fun doClean() {
        val logger = ToStringLogger.createWithLifecycle(project.gradle) { log ->
            val buildDir = project.buildDir
            if (buildDir.exists().not()) {
                buildDir.mkdirs()
            }
            val outputFile = buildDir.resolve(LOG_FILE_NAME)
            outputFile.writeText(log)
            println("wrote cleaning service log to ${outputFile.absolutePath}")
        }

        val tracker = DependencyTracker(project.rootProject, logger)
        val coveredProjectPaths = tracker.findAllDependents(project).map { it.projectDir.path }

        val removers = listOf(
            DrawableFileRemover(),
            LayoutFileRemover()
        )

        val config = config.getOrElse(CleaningServiceConfig())
        removers.map { it.remove(coveredProjectPaths, config) }
    }
}
