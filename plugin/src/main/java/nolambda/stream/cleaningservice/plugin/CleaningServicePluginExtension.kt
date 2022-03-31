package nolambda.stream.cleaningservice.plugin

import nolambda.stream.cleaningservice.CleaningServiceConfig
import nolambda.stream.cleaningservice.remover.AbstractRemover
import nolambda.stream.cleaningservice.remover.RemoverFactory
import nolambda.stream.cleaningservice.report.DefaultReportEngineFactory
import nolambda.stream.cleaningservice.report.PathResolver
import nolambda.stream.cleaningservice.utils.NoopLogger
import nolambda.stream.cleaningservice.utils.SimpleLogger
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Property
import org.gradle.api.provider.SetProperty
import java.io.File

@Suppress("MemberVisibilityCanBePrivate")
open class CleaningServicePluginExtension(factory: ObjectFactory) {

    val enableLog: Property<Boolean> = factory.property(Boolean::class.java).convention(true)
    val dryRun: Property<Boolean> = factory.property(Boolean::class.java).convention(true)
    val excludeNames: SetProperty<String> = factory.setProperty(String::class.java)

    internal val removerExtension = RemoverExtension()

    fun remover(action: Action<RemoverExtension>) {
        action.execute(removerExtension)
    }

    /**
     * Convert the plugin extension to data class format to enforce immutability
     * @param rootDir of the project, used for path resolving in report
     */
    fun toConfig(rootDir: File): CleaningServiceConfig {
        return CleaningServiceConfig(
            excludeNames = excludeNames.get(),
            dryRun = dryRun.get(),
            logger = if (enableLog.get()) SimpleLogger() else NoopLogger,
            reportEngineFactory = DefaultReportEngineFactory(
                reportDir = File(rootDir, DefaultReportEngineFactory.DEFAULT_DIR_NAME).path,
                pathResolver = PathResolver.RelativeToParent(rootDir)
            )
        )
    }

    companion object {

        private const val EXTENSION_NAME = "cleaningService"

        fun get(target: Project): CleaningServicePluginExtension {
            return target.extensions.create(EXTENSION_NAME, CleaningServicePluginExtension::class.java)
        }
    }
}

class RemoverExtension {

    val removers = mutableListOf<AbstractRemover>().apply {
        addAll(RemoverFactory.getAllRemovers())
    }

    fun xmlOnly() {
        removers.clear()
        removers.addAll(RemoverFactory.getXmlRemovers())
    }

    fun fileOnly() {
        removers.clear()
        removers.addAll(RemoverFactory.getFileRemovers())
    }

    fun essential() {
        removers.clear()
        removers.addAll(RemoverFactory.getEssentialRemover())
    }

    fun only(vararg classNames: String) {
        removers.clear()
        removers.addAll(classNames.map {
            Class.forName(it).getDeclaredConstructor().newInstance() as AbstractRemover
        })
    }
}
