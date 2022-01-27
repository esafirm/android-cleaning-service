package nolambda.stream.cleaningservice.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

class CleaningServicePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        if (target.parent != null) {
            error("Plugin must be applied to root project")
        }

        val ext = target.extensions.create("cleaningService", CleaningServicePluginExtension::class.java)

        target.subprojects.forEach {
            it.tasks.register("cleaningService", CleaningTask::class.java) { task ->
                task.config.set(ext.toConfig())
            }
        }
    }
}
