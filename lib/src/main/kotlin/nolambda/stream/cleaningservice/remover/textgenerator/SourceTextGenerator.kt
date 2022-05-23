package nolambda.stream.cleaningservice.remover.textgenerator

import java.io.File

class SourceTextGenerator(
    private val fileFilter: Regex = FILE_TYPE_FILTER,
    private val moduleSrcDirs: List<String>
) {

    companion object {
        private val FILE_TYPE_FILTER = Regex("(.*\\.xml)|(.*\\.kt)|(.*\\.java)|(.*\\.gradle)")
        private val IMPORT_ALIAS = Regex("\\.R\\.(\\w+) as ([a-zA-Z]+)")

        private const val EXT_KOTLIN = "kt"
    }

    private fun checkIfDirExists(dirs: List<String>) {
        dirs.forEach {
            val file = File(it)
            if (file.exists().not()) {
                error("Passed module directory: $it is not exits")
            }
        }
    }

    fun generate(): String {
        checkIfDirExists(moduleSrcDirs)

        val stringBuilder = StringBuilder()

        // This is mostly handle the gradle files
        moduleSrcDirs.map { File(it) }
            .filter { it.exists() && it.isDirectory.not() }
            .forEach { srcDirFile ->
                srcDirFile.listFiles { _, name ->
                    fileFilter.matches(name)
                }?.forEach { f ->
                    stringBuilder.append(f.readText().replace("\n", "").replace(" ", ""))
                }
            }

        // This will handle the rest of the type
        moduleSrcDirs.map { File("${it}/src") }
            .forEach { srcDirFile ->
                srcDirFile.walk().filter {
                    fileFilter.matches(it.name)
                }.forEach { f ->
                    stringBuilder.append(f.readTextContent())
                }
            }

        return stringBuilder.toString()
    }

    private fun File.readTextContent(): String {
        val plainText = readText()
        return if (extension == EXT_KOTLIN) {
            val matchResult = IMPORT_ALIAS.find(plainText) ?: return plainText
            val resourceName = matchResult.groupValues[1]
            val alias = matchResult.groupValues[2]

            plainText.replace(Regex("${alias}\\."), "${resourceName}.")
        } else {
            plainText
        }
    }
}
