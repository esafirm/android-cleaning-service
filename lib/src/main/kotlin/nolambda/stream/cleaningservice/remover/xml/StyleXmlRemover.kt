package nolambda.stream.cleaningservice.remover.xml

import nolambda.stream.cleaningservice.SearchPattern

class StyleXmlRemover : XmlValueRemover(
    fileType = "style",
    resourceName = "style",
    tagName = "style",
    type = SearchPattern.Type.STYLE
)
