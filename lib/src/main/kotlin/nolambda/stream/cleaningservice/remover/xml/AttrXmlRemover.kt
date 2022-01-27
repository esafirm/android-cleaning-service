package nolambda.stream.cleaningservice.remover.xml

class AttrXmlRemover : XmlValueRemover(
    fileType = "attr",
    resourceName = "styleable",
    tagName = "declare-styleable"
)
