package nolambda.stream.cleaningservice.report

class ReportEngine(
    val writer: ReportWriter<*>,
    val printer: ReportPrinter
)
