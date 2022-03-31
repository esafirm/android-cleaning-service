package nolambda.stream.cleaningservice.plugin

import nolambda.stream.cleaningservice.report.DefaultReportEngineFactory
import org.gradle.api.Plugin
import org.gradle.api.Project

class CleaningServicePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        if (target.parent != null) {
            error("Plugin must be applied to root project")
        }

        val ext = CleaningServicePluginExtension.get(target)

        target.subprojects.forEach {
            it.tasks.register("cleaningService", CleaningTask::class.java) { task ->
                task.extension.set(ext)
            }
        }

        target.tasks.register("cleaningServiceAll", CleaningAllTask::class.java) { task ->
            task.extension.set(ext)
        }

        target.tasks.register("cleaningServiceFromReport", CleaningFromReportTask::class.java) { task ->
            task.extension.set(ext)

            val projectDir = target.layout.projectDirectory
            task.reportDir.set(target.provider {
                val dir = System.getProperty("cleaningReportDir", "")
                projectDir.dir(dir.ifBlank{ DefaultReportEngineFactory.DEFAULT_DIR_NAME })
            })
        }
    }
}
