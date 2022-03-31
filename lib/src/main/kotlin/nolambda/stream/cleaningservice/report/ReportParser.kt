package nolambda.stream.cleaningservice.report

import java.io.File

interface ReportParser {
    fun parse(): List<DeleteAction>

    class DeleteAction(
        val file: File,
        val type: RemoverType,
        val id: String
    )

    enum class RemoverType {
        XML,
        FILE
    }
}

class DefaultReportParser(
    private val projectDir: File,
    private val reportDir: File
) : ReportParser {

    override fun parse(): List<ReportParser.DeleteAction> {
        val reportFiles = reportDir.listFiles().orEmpty()
        return reportFiles.flatMap {

            val identifier = it.name.split("_").last().replace(".csv", "")
            val removerType = identifierToRemover(identifier)
            val report = it.readLines()

            report.map { line ->
                val values = line.split(",")
                val path = values.last()

                ReportParser.DeleteAction(
                    file = File(projectDir, path),
                    type = removerType,
                    id = values.getId(removerType)
                )
            }
        }
    }

    private fun List<String>.getId(removerType: ReportParser.RemoverType): String {
        if (removerType == ReportParser.RemoverType.FILE) return ""
        return get(1).trim()
    }

    private fun identifierToRemover(identifier: String): ReportParser.RemoverType {
        return when (identifier) {
            "layout" -> ReportParser.RemoverType.FILE
            "string" -> ReportParser.RemoverType.XML
            else -> error(
                "There's no remover with identifier \"$identifier\". " +
                    "Please don't change the result name because the remover rely on the naming convention"
            )
        }
    }
}
