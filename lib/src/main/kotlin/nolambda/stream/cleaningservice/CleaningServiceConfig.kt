package nolambda.stream.cleaningservice

import nolambda.stream.cleaningservice.remover.AbstractRemover
import nolambda.stream.cleaningservice.report.ReportEngine
import nolambda.stream.cleaningservice.utils.Logger
import nolambda.stream.cleaningservice.utils.SimpleLogger

class CleaningServiceConfig(
    val extraRemovers: List<AbstractRemover> = emptyList(),
    val excludeNames: Set<String> = emptySet(),
    val dryRun: Boolean = true,
    val logger: Logger = SimpleLogger()
)
