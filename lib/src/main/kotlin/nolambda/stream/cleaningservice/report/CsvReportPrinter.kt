package nolambda.stream.cleaningservice.report

import java.io.File

class CsvReportPrinter(
    private val writer: ReportWriter<List<String>>,
    private val destDir: String,
    private val destFileName: String
) : ReportPrinter {

    override fun print() {
        val reportText = writer.dump().joinToString(separator = "\n")
        val reportFile = File(destDir, destFileName)
        if (reportFile.exists()) {
            reportFile.delete()
        }
        if (reportText.isNotBlank()) {
            reportFile.appendText(reportText)
        }
    }

}
