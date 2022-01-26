package nolambda.stream.cleaningservice.components.file

import nolambda.stream.cleaningservice.SearchPattern

class MipmapFileRemover : FileRemover(
    fileType = "mipmap",
    resourceName = "mipmap",
    type = SearchPattern.Type.DRAWABLE
)
