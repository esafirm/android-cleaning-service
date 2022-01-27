package nolambda.stream.cleaningservice.report

interface ReportWriter<OUT> {
    fun write(vararg reports: String)
    fun dump(): OUT
}
