package nolambda.stream.cleaningservice.components.xml

import nolambda.stream.cleaningservice.SearchPattern

class StyleXmlRemover : XmlValueRemover(
    fileType = "style",
    resourceName = "style",
    tagName = "style",
    type = SearchPattern.Type.STYLE
)
