package nolambda.stream.cleaningservice.remover.file

import nolambda.stream.cleaningservice.remover.AbstractRemover
import nolambda.stream.cleaningservice.utils.DirectoryMatcher
import nolambda.stream.cleaningservice.SearchPattern
import java.io.File

open class FileRemover(
    fileType: String,
    resourceName: String,
    type: SearchPattern.Type = SearchPattern.Type.DEFAULT,
) : AbstractRemover(fileType, resourceName, type) {

    override fun removeEach(resDirFile: File) {
        var checkResult = false

        resDirFile.walk().filter { it.isDirectory }.forEach { dir ->
            if (DirectoryMatcher.matchLast(dir.path, fileType)) {
                dir.walk().forEach { f ->
                    if (f.isDirectory.not()) {
                        logger.logDev("Check ${f.name}")
                        val result = removeFileIfNeed(f)
                        checkResult = checkResult || result
                    }
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
