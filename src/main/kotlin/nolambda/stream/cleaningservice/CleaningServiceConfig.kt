package nolambda.stream.cleaningservice

import nolambda.stream.cleaningservice.components.AbstractRemover
import nolambda.stream.cleaningservice.components.file.FileRemover

data class CleaningServiceConfig(
    val extraRemovers: List<AbstractRemover> = listOf(),
    val excludeNames: List<String> = listOf(),
    val dryRun: Boolean = true
)