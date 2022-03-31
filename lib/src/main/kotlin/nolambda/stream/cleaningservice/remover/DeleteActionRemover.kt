package nolambda.stream.cleaningservice.remover

import nolambda.stream.cleaningservice.remover.utils.XmlRemoverUtils
import nolambda.stream.cleaningservice.report.ReportParser
import nolambda.stream.cleaningservice.utils.Logger
import org.jdom2.Content
import org.jdom2.Element
import org.jdom2.Text
import java.io.File

/**
 * A remover that act base on [ReportParser.DeleteAction]
 */
class DeleteActionRemover(
    private val logger: Logger
) {

    fun remove(items: List<ReportParser.DeleteAction>) {
        val actions = items.groupBy { it.type }

        actions[ReportParser.RemoverType.FILE]?.forEach { action ->
            removeFile(action)
        }

        val allXmlActions = actions[ReportParser.RemoverType.XML].orEmpty()
        val actionByFile = allXmlActions.groupBy { it.file }

        actionByFile.entries.forEach {
            removeTagsInFile(it.key, it.value)
        }
    }

    private fun removeFile(action: ReportParser.DeleteAction) {
        logger.logDev("Removing ${action.file}")
        action.file.delete()
    }

    private fun removeTagsInFile(xmlFile: File, actions: List<ReportParser.DeleteAction>) {
        XmlRemoverUtils.runXmlCleaning(xmlFile) { doc ->
            val iterator = doc.rootElement.content.iterator()
            var isAfterRemoved = false

            while (iterator.hasNext()) {
                val content = iterator.next()

                // Remove line break after element is removed
                if (isAfterRemoved && content?.cType == Content.CType.Text) {
                    val text = content as Text
                    if (text.text.contains("\n")) {
                        iterator.remove()
                    }
                    isAfterRemoved = false
                } else if (content?.cType == Content.CType.Element) {
                    val element = content as Element
                    val name = element.getAttribute("name").value

                    val needToRemove = actions.find { it.id == name } != null
                    if (needToRemove) {
                        logger.logDev("Removing XML entry with name: $name in ${xmlFile.path}")
                        iterator.remove()
                        isAfterRemoved = true
                    }
                }
            }
        }
    }

}
