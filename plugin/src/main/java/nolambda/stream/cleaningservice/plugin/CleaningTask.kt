package nolambda.stream.cleaningservice.plugin

import nolambda.stream.cleaningservice.plugin.utils.DependencyTracker
import nolambda.stream.cleaningservice.plugin.utils.ToStringLogger
import org.gradle.api.DefaultTask
import org.gradle.api.logging.LogLevel
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

abstract class CleaningTask : DefaultTask() {

    companion object {
        private const val LOG_FILE_NAME = "cleaning_service_log.txt"
    }

    @get:Input
    abstract val extension: Property<CleaningServicePluginExtension>

    @TaskAction
    fun doClean() {
        checkConfigureOnDemand()

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
        val scopeProjectPaths = tracker.findAllDependents(project).map { it.projectDir.path }

        val pluginExtension = extension.get()
        val removers = pluginExtension.removerExtension.removers
        val config = pluginExtension.toConfig()

        val targetPaths = listOf(project.projectDir.path)

        project.logger.log(LogLevel.LIFECYCLE, """
            ============================================================
            Removers: ${removers.joinToString { it.javaClass.simpleName }}
            Scope: ${scopeProjectPaths.joinToString()}
            Target: ${targetPaths.joinToString()}
            Config: $config
            ============================================================
        """.trimIndent())

        removers.forEach {
            it.remove(
                scopeModuleDirs = scopeProjectPaths,
                targetModulesDirs = targetPaths,
                extension = config
            )
        }
    }

    private fun checkConfigureOnDemand() {
        val isConfigureOnDemand = project.rootProject.properties["org.gradle.configureondemand"] == "true"
        if (isConfigureOnDemand) {
            error("""
                Cannot use module specific cleaning service if org.gradle.configureondemand=true
                Please disable configure on demand first, or use root cleaningServiceAll task
            """.trimIndent())
        }
    }
}
