package nolambda.stream.cleaningservice.remover.file

import nolambda.stream.cleaningservice.SearchPattern

class LayoutFileRemover : FileRemover(
    fileType = "layout",
    resourceName = "layout",
    type = SearchPattern.Type.LAYOUT
)
