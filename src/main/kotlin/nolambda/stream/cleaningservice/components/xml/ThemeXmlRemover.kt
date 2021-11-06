package nolambda.stream.cleaningservice.components.xml

import nolambda.stream.cleaningservice.SearchPattern

class ThemeXmlRemover : XmlValueRemover(
    fileType = "style",
    resourceName = "style",
    tagName = "style",
    type = SearchPattern.Type.STYLE
)
