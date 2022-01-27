package nolambda.stream.cleaningservice

import nolambda.stream.cleaningservice.remover.AbstractRemover
import nolambda.stream.cleaningservice.report.ReportEngine
import nolambda.stream.cleaningservice.utils.Logger
import nolambda.stream.cleaningservice.utils.SimpleLogger

class CleaningServiceConfig(
    val extraRemovers: List<AbstractRemover> = listOf(),
    val excludeNames: List<String> = listOf(),
    val dryRun: Boolean = true,
    val reportEngineFactory: () -> ReportEngine = { ReportEngine.default() },
    val logger: Logger = SimpleLogger()
)
