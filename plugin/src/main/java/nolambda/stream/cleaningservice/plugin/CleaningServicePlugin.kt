package nolambda.stream.cleaningservice.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

class CleaningServicePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        if (target.rootDir != target.rootProject.rootDir) {
            error("Plugin must be applied to root project")
        }

        target.subprojects.forEach {
            it.tasks.register("cleaningService", CleaningTask::class.java)
        }
    }
}
