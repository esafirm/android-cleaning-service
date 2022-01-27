package nolambda.stream.cleaningservice.report

class ReportEngine(
    val writer: ReportWriter<*>,
    val printer: ReportPrinter
) {

    companion object {
        private const val DEFAULT_DIR_NAME = "build"

        fun create(
            reportDir: String = DEFAULT_DIR_NAME,
            reportFileName: String
        ): ReportEngine {
            val writer = CsvReportWriter()
            val printer = CsvReportPrinter(writer, reportDir, reportFileName)
            return ReportEngine(writer, printer)
        }
    }
}
