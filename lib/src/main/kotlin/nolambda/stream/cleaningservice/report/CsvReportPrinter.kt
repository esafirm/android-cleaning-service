package nolambda.stream.cleaningservice.report

import java.io.File

class CsvReportPrinter(
    private val writer: ReportWriter<List<Array<out String>>>,
    private val destDir: String,
    private val destFileName: String
) : ReportPrinter {

    override fun print() {
        val reportText = writer.dump().joinToString("\n") { it.joinToString(separator = ",") }
        val reportFile = File(destDir, destFileName)
        if (reportFile.exists()) {
            reportFile.delete()
        }
        if (reportText.isNotBlank()) {
            reportFile.appendText(reportText)
        }
    }
}
