package nolambda.stream.cleaningservice

import nolambda.stream.cleaningservice.utils.Logger
import nolambda.stream.cleaningservice.utils.SimpleLogger

class CleaningServiceConfig(
    val excludeNames: Set<String> = emptySet(),
    val dryRun: Boolean = true,
    val logger: Logger = SimpleLogger()
)
