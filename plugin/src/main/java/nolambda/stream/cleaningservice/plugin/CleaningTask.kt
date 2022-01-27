package nolambda.stream.cleaningservice.plugin

import nolambda.stream.cleaningservice.CleaningServiceConfig
import nolambda.stream.cleaningservice.remover.file.DrawableFileRemover
import nolambda.stream.cleaningservice.remover.file.LayoutFileRemover
import nolambda.stream.cleaningservice.plugin.utils.DependencyTracker
import nolambda.stream.cleaningservice.plugin.utils.ToStringLogger
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

abstract class CleaningTask : DefaultTask() {

    companion object {
        private const val LOG_FILE_NAME = "cleaning_service_log.txt"
    }

    @TaskAction
    fun doClean() {
        val logger = ToStringLogger.createWithLifecycle(project.gradle) { log ->
            val buildDir = project.buildDir
            val outputFile = buildDir.resolve(LOG_FILE_NAME)
            outputFile.writeText(log)
            println("wrote cleaning service log to ${outputFile.absolutePath}")
        }

        val tracker = DependencyTracker(project.rootProject, logger)
        val coveredProjectPaths = tracker.findAllDependents(project).map { it.projectDir.path }

        val extension = CleaningServiceConfig()
        val removers = listOf(
            DrawableFileRemover(),
            LayoutFileRemover()
        )

        removers.map { it.remove(coveredProjectPaths, extension) }
    }
}
