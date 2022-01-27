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

abstract class CleaningAllTask : DefaultTask() {

    @get:Input
    abstract val config: Property<CleaningServiceConfig>

    @TaskAction
    fun doClean() {
        val coveredProjects = project.subprojects - project
        val coveredProjectPaths = coveredProjects.map { it.projectDir.path }

        val removers = listOf(
            DrawableFileRemover(),
            LayoutFileRemover()
        )

        val config = config.getOrElse(CleaningServiceConfig())
        removers.map { it.remove(coveredProjectPaths, config) }
    }
}
