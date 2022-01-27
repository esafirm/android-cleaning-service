package nolambda.stream.cleaningservice.plugin.utils

import org.gradle.api.Project
import java.io.File

/**
 * The DIST_DIR is where you want to save things from the build. The build server will copy
 * the contents of DIST_DIR to somewhere and make it available.
 */
fun Project.getDistributionDirectory(): File {
    return project.rootProject.buildDir.also { dir ->
        if (dir.exists().not()) {
            dir.mkdirs()
        }
    }
}
