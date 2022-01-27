package nolambda.stream.cleaningservice.plugin

import nolambda.stream.cleaningservice.CleaningServiceConfig
import nolambda.stream.cleaningservice.utils.NoopLogger
import nolambda.stream.cleaningservice.utils.SimpleLogger
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Property
import org.gradle.api.provider.SetProperty

@Suppress("MemberVisibilityCanBePrivate")
open class CleaningServicePluginExtension(factory: ObjectFactory) {

    val enableLog: Property<Boolean> = factory.property(Boolean::class.java).convention(true)
    val dryRun: Property<Boolean> = factory.property(Boolean::class.java).convention(true)
    val excludeNames: SetProperty<String> = factory.setProperty(String::class.java)

    fun toConfig(): CleaningServiceConfig {
        return CleaningServiceConfig(
            excludeNames = excludeNames.get(),
            dryRun = dryRun.get(),
            logger = if (enableLog.get()) SimpleLogger() else NoopLogger,
        )
    }
}
