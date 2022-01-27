package nolambda.stream.cleaningservice.report

class ReportEngine(
    val writer: ReportWriter<*>,
    val printer: ReportPrinter
) {

    companion object {
        fun default(): ReportEngine {
            val writer = CsvReportWriter()
            val printer = CsvReportPrinter(writer)
            return ReportEngine(writer, printer)
        }
    }
}
