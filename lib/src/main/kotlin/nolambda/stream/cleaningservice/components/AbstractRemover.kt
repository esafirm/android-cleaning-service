package nolambda.stream.cleaningservice.components

import nolambda.stream.cleaningservice.utils.Logger
import nolambda.stream.cleaningservice.SearchPattern
import nolambda.stream.cleaningservice.CleaningServiceConfig
import java.io.File

abstract class AbstractRemover(

    /**
     * directory/file name to find files like drawable, dimen, string
     */
    val fileType: String,

    /**
     * Resource name to check its existence like @`string`/app_name, $.`string`/app_name
     */
    val resourceName: String,


    /**
     * Search pattern
     * ex) theme should specified to Type.STYLE
     */
    val type: SearchPattern.Type
) {

    companion object {
        private val FILE_TYPE_FILTER = Regex("(.*\\.xml)|(.*\\.kt)|(.*\\.java)|(.*\\.gradle)")
        private const val DEBUG_MODE = true

        fun createScanTargetFileTexts(moduleSrcDirs: List<String>): String {
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
                .filter {
                    val exist = it.exists()
                    println("${it.path} exists: $exist")
                    exist
                }
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

        scanTargetFileTexts = createScanTargetFileTexts(moduleSrcDirs)

        val targetDir = File("./build").apply {
            mkdirs()
        }
        val targetFile = File(targetDir, "target_file")
        targetFile.writeText(scanTargetFileTexts)

        Logger.log("[$fileType] ======== Start $fileType checking ========")

        moduleSrcDirs.forEach {
            val moduleSrcName = it
            Logger.log("[$fileType] $moduleSrcName")

            val resDirFile = File("${it}/src/main/res")
            if (resDirFile.exists()) {
                removeEach(resDirFile)
            }
        }
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
