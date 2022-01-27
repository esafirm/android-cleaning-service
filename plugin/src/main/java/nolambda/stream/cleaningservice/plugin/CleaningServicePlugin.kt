package nolambda.stream.cleaningservice.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

class CleaningServicePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        if (target.parent != null) {
            error("Plugin must be applied to root project")
        }

        val ext = target.extensions.create("cleaningService", CleaningServicePluginExtension::class.java)
        val config = ext.toConfig()

        target.subprojects.forEach {
            it.tasks.register("cleaningService", CleaningTask::class.java) { task ->
                task.config.set(config)
            }
        }

        target.tasks.register("cleaningServiceAll", CleaningAllTask::class.java) { task ->
            task.config.set(config)
        }
    }
}
