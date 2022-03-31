package nolambda.stream.cleaningservice

import nolambda.stream.cleaningservice.report.DefaultReportEngineFactory
import nolambda.stream.cleaningservice.report.ReportEngineFactory
import nolambda.stream.cleaningservice.utils.Logger
import nolambda.stream.cleaningservice.utils.SimpleLogger

data class CleaningServiceConfig(
    val excludeNames: Set<String> = emptySet(),
    val dryRun: Boolean = true,
    val logger: Logger = SimpleLogger(),
    val reportEngineFactory: ReportEngineFactory = DefaultReportEngineFactory()
)
