package nolambda.stream.cleaningservice.components.xml

class AttrXmlRemover : XmlValueRemover(
    fileType = "attr",
    resourceName = "styleable",
    tagName = "declare-styleable"
)
