package nolambda.stream.cleaningservice.report

import java.io.File

class CsvReportPrinter(
    private val writer: ReportWriter<List<Array<out String>>>,
    private val destDir: String = DEFAULT_DIR_NAME,
    private val destFileName: String = DEFAULT_FILE_NAME
) : ReportPrinter {

    companion object {
        private const val DEFAULT_FILE_NAME = "cleaning_service_report.txt"
        private const val DEFAULT_DIR_NAME = "build"
    }

    override fun print() {
        val reportText = writer.dump().joinToString("\n") { it.joinToString(separator = ",") }
        val reportFile = File(destDir, destFileName)
        reportFile.appendText(reportText)
    }
}
