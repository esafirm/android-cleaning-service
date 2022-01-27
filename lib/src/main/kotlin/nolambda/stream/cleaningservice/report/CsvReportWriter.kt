package nolambda.stream.cleaningservice.report

class CsvReportWriter : ReportWriter<List<Array<out String>>> {

    private val reportLines = mutableListOf<Array<out String>>()

    override fun write(vararg reports: String) {
        reportLines.add(reports)
    }

    override fun dump(): List<Array<out String>> = reportLines
}
