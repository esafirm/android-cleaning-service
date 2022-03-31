package nolambda.stream.cleaningservice.plugin

import org.gradle.api.DefaultTask
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

abstract class CleaningAllTask : DefaultTask() {

    @get:Input
    abstract val extension: Property<CleaningServicePluginExtension>

    @TaskAction
    fun doClean() {
        val coveredProjects = project.subprojects - project
        val coveredProjectPaths = coveredProjects.map { it.projectDir.path }

        val pluginExtension = extension.get()
        val removers = pluginExtension.removerExtension.removers

        val config = pluginExtension.toConfig(project.rootDir)
        removers.map { it.remove(coveredProjectPaths, coveredProjectPaths, config) }
    }
}
