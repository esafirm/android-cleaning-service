package nolambda.stream.cleaningservice.remover.utils

import org.jdom2.Document
import org.jdom2.Verifier
import org.jdom2.input.SAXBuilder
import org.jdom2.output.Format
import org.jdom2.output.LineSeparator
import org.jdom2.output.XMLOutputter
import java.io.File
import java.io.StringWriter

object XmlRemoverUtils {
    /**
     * Remove the resource file if the element is empty
     * @return true if the file is deleted
     */
    fun removeFileIfNeeded(file: File): Boolean {
        val doc = SAXBuilder().build(file)
        if (doc.rootElement.children.size == 0) {
            file.delete()
            return true
        }
        return false
    }

    /**
     * Escape numeric entities from xml file
     * @see https://stackoverflow.com/questions/29127660/keep-numeric-character-entity-characters-such-as-10-13-when-parsing-xml
     *
     * @return true if there's value that escaped
     */
    fun escapeNumericEntity(file: File): Boolean {
        var isEscaped = false
        val regex = Regex("&(.*?);")
        val text = file.readText().replace(regex) { result ->
            isEscaped = true
            "{{${result.groupValues[1]}}}"
        }
        file.writeText(text)
        return isEscaped
    }

    /**
     * Unescape previously escaped numeric entities
     */
    fun unescapeNumericEntity(file: File) {
        val regex = Regex("\\{\\{(.*?)}}")
        val text = file.readText().replace(regex) { result ->
            result.groupValues
            "&${result.groupValues[1]};"
        }
        file.writeText(text)
    }

    /**
     * Save the [doc] to a [file] in Android resource XML way
     * @param doc the xml document
     * @param file the destination file
     */
    fun saveFile(doc: Document, file: File) {
        val stringWWriter = StringWriter()

        XMLOutputter().apply {
            format = Format.getRawFormat()
            format.setLineSeparator(LineSeparator.SYSTEM)
            format.textMode = Format.TextMode.PRESERVE
            format.encoding = "utf-8"
            format.setEscapeStrategy { ch ->
                Verifier.isHighSurrogate(ch)
            }
            output(doc, stringWWriter)
        }

        file.writeText(stringWWriter.toString().replaceFirst(Regex("\\n\\s+</resources>"), "\n</resources>"))
    }

    fun runXmlCleaning(xmlFile: File, action: (doc: Document) -> Unit) {
        // Escape the XML First
        val isEscaped = escapeNumericEntity(xmlFile)

        val doc = SAXBuilder().build(xmlFile)
        action(doc)
        saveFile(doc, xmlFile)

        val isFileRemoved = removeFileIfNeeded(xmlFile)
        if (!isFileRemoved && isEscaped) {
            unescapeNumericEntity(xmlFile)
        }
    }
}
