package nolambda.stream.cleaningservice.components.xml

class IdXmlRemover : XmlValueRemover(
    fileType = "id",
    resourceName = "id",
    tagName = "item"
)