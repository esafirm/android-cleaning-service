package nolambda.stream.cleaningservice.remover.file

import nolambda.stream.cleaningservice.SearchPattern
import nolambda.stream.cleaningservice.remover.AbstractRemover
import nolambda.stream.cleaningservice.report.ReportEngine
import nolambda.stream.cleaningservice.utils.DirectoryMatcher
import java.io.File

open class FileRemover(
    fileType: String,
    resourceName: String,
    type: SearchPattern.Type = SearchPattern.Type.DEFAULT,
    reportEngine: ReportEngine = ReportEngine.create(
        reportFileName = "cleaning_report_file_$fileType.csv"
    )
) : AbstractRemover(fileType, resourceName, type, reportEngine) {

    override fun removeEach(resDirFile: File) {
        var checkResult = false

        resDirFile.walk().filter { it.isDirectory }.forEach { dir ->
            if (DirectoryMatcher.matchLast(dir.path, fileType)) {

                val listFiles = dir.listFiles { file -> file.isDirectory.not() }.orEmpty()
                val checkFileSize = listFiles.size

                logger.logDev("Files needed to check: $checkFileSize")

                listFiles.forEachIndexed { index, f ->
                    logger.logDev("${index + 1}/$checkFileSize Check ${f.name}")
                    val result = removeFileIfNeed(f)
                    checkResult = checkResult || result
                }
            }
        }

        if (checkResult) {
            logger.log("[$fileType]   Complete to remove files.")
        } else {
            logger.log("[$fileType]   No unused $fileType files.")
        }
    }

    private fun removeFileIfNeed(file: File): Boolean {
        if (isMatchedExcludeNames(file.path)) {
            logger.logYellow("[${fileType}]   Ignore checking ${file.name}")
            return false
        }
        val isMatched = checkTargetTextMatches(extractFileName(file))
        return if (!isMatched) {
            logger.logGreen("[${fileType}]   Remove ${file.name}")
            reportWriter.write(fileType, file.name)
            if (!dryRun) {
                file.delete()
            }
            true
        } else {
            false
        }
    }

    private fun extractFileName(file: File): String {
        val index = file.name.lastIndexOf('.')
        return if (index < 0) {
            file.name
        } else {
            file.name.take(index)
        }
    }
}
