package nolambda.stream.cleaningservice.remover

import nolambda.stream.cleaningservice.CleaningServiceConfig
import nolambda.stream.cleaningservice.SearchPattern
import nolambda.stream.cleaningservice.report.ReportEngine
import nolambda.stream.cleaningservice.report.ReportWriter
import nolambda.stream.cleaningservice.utils.Logger
import java.io.File

/**
 * @param fileType directory/file name to find files like drawable, dimen, string
 * @param resourceName Resource name to check its existence like @`string`/app_name, $.`string`/app_name
 * @param type * Search pattern. ex) theme should specified to Type.STYLE
 */
abstract class AbstractRemover(
    val fileType: String,
    val resourceName: String,
    val type: SearchPattern.Type,
    private val reportEngine: ReportEngine
) {

    companion object {
        private val FILE_TYPE_FILTER = Regex("(.*\\.xml)|(.*\\.kt)|(.*\\.java)|(.*\\.gradle)")

        private fun checkIfDirExists(dirs: List<String>) {
            dirs.forEach {
                val file = File(it)
                if (file.exists().not()) {
                    error("Passed module directory: $it is not exits")
                }
            }
        }

        fun createScanTargetFileTexts(moduleSrcDirs: List<String>): String {
            checkIfDirExists(moduleSrcDirs)

            val stringBuilder = StringBuilder()

            moduleSrcDirs.map { File(it) }
                .filter { it.exists() }
                .forEach { srcDirFile ->
                    srcDirFile.listFiles { _, name ->
                        FILE_TYPE_FILTER.matches(name)
                    }?.forEach { f ->
                        stringBuilder.append(f.readText().replace("\n", "").replace(" ", ""))
                    }
                }

            moduleSrcDirs.map { File("${it}/src") }
                .forEach { srcDirFile ->
                    srcDirFile.walk().filter {
                        FILE_TYPE_FILTER.matches(it.name)
                    }.forEach { f ->
                        stringBuilder.append(f.readText())
                    }
                }

            return stringBuilder.toString()
        }
    }

    // Visible for testing
    internal var scanTargetFileTexts = ""

    // Extension settings
    private var excludeNames = mutableListOf<String>()
    internal var dryRun = false

    protected lateinit var logger: Logger
        private set

    protected val reportWriter: ReportWriter<*> = reportEngine.writer

    abstract fun removeEach(resDirFile: File)

    /**
     * @param target is file name or attribute name
     * @return pattern string to grep src
     */
    private fun createSearchPattern(target: String): Regex {
        return SearchPattern.create(resourceName, target, type)
    }

    fun remove(moduleSrcDirs: List<String>, extension: CleaningServiceConfig) {
        this.dryRun = extension.dryRun
        this.excludeNames = extension.excludeNames.toMutableList()
        this.logger = extension.logger

        scanTargetFileTexts = createScanTargetFileTexts(moduleSrcDirs)

        val targetDir = File("./build").apply {
            mkdirs()
        }
        val targetFile = File(targetDir, "target_file")
        targetFile.writeText(scanTargetFileTexts)

        logger.log("[$fileType] ======== Start $fileType checking ========")

        moduleSrcDirs.forEach {
            val moduleSrcName = it
            logger.log("[$fileType] $moduleSrcName")

            val resDirFile = File("${it}/src/main/res")
            if (resDirFile.exists()) {
                removeEach(resDirFile)
            }
        }

        // Print the report
        reportEngine.printer.print()
    }

    internal fun checkTargetTextMatches(targetText: String): Boolean {
        val pattern = createSearchPattern(targetText)
        return pattern.containsMatchIn(scanTargetFileTexts)
    }

    protected fun isMatchedExcludeNames(filePath: String): Boolean {
        return excludeNames.any { filePath.contains(it) }
    }

    override fun toString(): String {
        return "fileType: ${fileType}, resourceName: ${resourceName}, type: $type"
    }
}
