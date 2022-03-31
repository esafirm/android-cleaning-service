package nolambda.stream.cleaningservice.report

import java.io.File

interface ReportWriter<OUT> {
    fun write(file: File, vararg extraInfo: String)
    fun dump(): OUT
}
