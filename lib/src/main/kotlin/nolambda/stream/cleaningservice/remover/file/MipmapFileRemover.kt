package nolambda.stream.cleaningservice.remover.file

import nolambda.stream.cleaningservice.SearchPattern

class MipmapFileRemover : FileRemover(
    fileType = "mipmap",
    resourceName = "mipmap",
    type = SearchPattern.Type.DRAWABLE
)
