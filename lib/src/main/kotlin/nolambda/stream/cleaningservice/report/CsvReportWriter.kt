package nolambda.stream.cleaningservice.report

import java.io.File

class CsvReportWriter(
    private val pathResolver: PathResolver
) : ReportWriter<List<String>> {

    private val reportLines = mutableListOf<String>()

    override fun write(file: File, vararg extraInfo: String) {
        val line = "${extraInfo.joinToString()},${pathResolver.resolve(file)}"
        reportLines.add(line)
    }

    override fun dump(): List<String> = reportLines
}
