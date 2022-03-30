package nolambda.stream.cleaningservice.report

import nolambda.stream.cleaningservice.remover.AbstractRemover
import java.io.File

interface ReportEngineFactory {
    fun create(remover: AbstractRemover): ReportEngine
}

class DefaultReportEngineFactory(
    private val reportDir: String = DEFAULT_DIR_NAME,
    private val pathResolver: PathResolver = PathResolver.Simple
) : ReportEngineFactory {

    companion object {
        const val DEFAULT_DIR_NAME = "build/cleaning"
    }

    override fun create(remover: AbstractRemover): ReportEngine {
        // Create report dir if not exists
        val dirName = File(DEFAULT_DIR_NAME)
        if (dirName.exists().not()) {
            dirName.mkdirs()
        }

        val reportFileName = "cleaning_report_${remover.fileType}.csv"

        val writer = CsvReportWriter(pathResolver)
        val printer = CsvReportPrinter(writer, reportDir, reportFileName)
        return ReportEngine(writer, printer)
    }
}
