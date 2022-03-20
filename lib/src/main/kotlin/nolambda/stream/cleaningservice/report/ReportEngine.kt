package nolambda.stream.cleaningservice.report

import java.io.File

class ReportEngine(
    val writer: ReportWriter<*>,
    val printer: ReportPrinter
) {

    companion object {
        const val DEFAULT_DIR_NAME = "build/cleaning"

        fun create(
            reportDir: String = DEFAULT_DIR_NAME,
            reportFileName: String
        ): ReportEngine {

            // Create report dir if not exists
            val dirName = File(DEFAULT_DIR_NAME)
            if (dirName.exists().not()) {
                dirName.mkdirs()
            }

            val writer = CsvReportWriter()
            val printer = CsvReportPrinter(writer, reportDir, reportFileName)
            return ReportEngine(writer, printer)
        }
    }
}
