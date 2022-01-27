package nolambda.stream.cleaningservice.remover.xml

import nolambda.stream.cleaningservice.SearchPattern

class ThemeXmlRemover : XmlValueRemover(
    fileType = "style",
    resourceName = "style",
    tagName = "style",
    type = SearchPattern.Type.STYLE
)
