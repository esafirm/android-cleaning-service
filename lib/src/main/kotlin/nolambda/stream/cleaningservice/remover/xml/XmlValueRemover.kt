package nolambda.stream.cleaningservice.remover.xml

import nolambda.stream.cleaningservice.SearchPattern
import nolambda.stream.cleaningservice.remover.AbstractRemover
import nolambda.stream.cleaningservice.utils.DirectoryMatcher
import org.jdom2.Content
import org.jdom2.Document
import org.jdom2.Element
import org.jdom2.Namespace
import org.jdom2.Text
import org.jdom2.Verifier
import org.jdom2.input.SAXBuilder
import org.jdom2.output.Format
import org.jdom2.output.LineSeparator
import org.jdom2.output.XMLOutputter
import java.io.File
import java.io.StringWriter

/**
 * @param tagName: Tag name to extract value from xml like <`dimen` name="width">, <`string` name="app_name">
 */
open class XmlValueRemover(
    fileType: String,
    resourceName: String,
    private val tagName: String,
    type: SearchPattern.Type = SearchPattern.Type.DEFAULT
) : AbstractRemover(fileType, resourceName, type) {

    companion object {
        private val TOOLS_NAMESPACE = Namespace.getNamespace("tools", "http://schemas.android.com/tools")
    }

    override fun removeEach(resDirFile: File) {
        resDirFile.walk().filter { it.isDirectory }.forEach { dir ->
            if (DirectoryMatcher.matchLast(dir.path, "values")) {
                dir.walk().forEach { f ->
                    if (f.isDirectory.not()) {
                        removeTagIfNeeded(f)
                    }
                }
            }
        }
    }

    private fun removeTagIfNeeded(file: File) {
        if (isMatchedExcludeNames(file.path)) {
            logger.logYellow("[${fileType}]   Ignore checking ${file.name}")
            return
        }

        var isFileChanged = false

        val doc = SAXBuilder().build(file)
        val iterator = doc.rootElement.content.iterator()

        var isAfterRemoved = false

        while (iterator.hasNext()) {
            val content = iterator.next()

            // Remove line break after element is removed
            if (isAfterRemoved && content?.cType == Content.CType.Text) {
                val text = content as Text
                if (text.text.contains("\n")) {
                    if (!dryRun) {
                        iterator.remove()
                    }
                }
                isAfterRemoved = false

            } else if (content?.cType == Content.CType.Element) {
                val element = content as Element

                logger.logDev("Element: ${element.name} ${element.value}")

                if (element.name == tagName) {
                    val attr = element.getAttribute("name")

                    if (attr != null) {
                        // Check the element has tools:override attribute
                        if (element.getAttribute("override", TOOLS_NAMESPACE)?.value == "true") {
                            logger.logYellow("[${fileType}]   Skip checking ${attr.value} which has tools:override in ${file.name}")
                            continue
                        }

                        val isMatched = checkTargetTextMatches(attr.value)
                        if (!isMatched) {
                            logger.logGreen("[${fileType}]   Remove ${attr.value} in ${file.name}")
                            if (!dryRun) {
                                iterator.remove()
                            }
                            isAfterRemoved = true
                            isFileChanged = true
                        }
                    }
                }
            }
        }

        if (isFileChanged) {
            if (!dryRun) {
                saveFile(doc, file)
                removeFileIfNeeded(file)
            }
        } else {
            logger.log("[${fileType}]   No unused tags in ${file.name}")
        }
    }

    private fun saveFile(doc: Document, file: File) {
        val stringWWriter = StringWriter()

        XMLOutputter().apply {
            format = Format.getRawFormat()
            format.setLineSeparator(LineSeparator.SYSTEM)
            format.setTextMode(Format.TextMode.PRESERVE)
            format.setEncoding("utf-8")
            format.setEscapeStrategy { ch ->
                Verifier.isHighSurrogate(ch)
            }
            output(doc, stringWWriter)
        }

        file.writeText(stringWWriter.toString().replaceFirst(Regex("\\n\\s+</resources>"), "\n</resources>"))
    }

    private fun removeFileIfNeeded(file: File) {
        val doc = SAXBuilder().build(file)
        if (doc.rootElement.getChildren(tagName).size == 0) {
            logger.logGreen("[${fileType}]   Remove ${file.name}.")
            file.delete()
        }
    }
}
