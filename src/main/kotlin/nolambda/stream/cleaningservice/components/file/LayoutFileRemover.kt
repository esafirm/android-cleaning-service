package nolambda.stream.cleaningservice.components.file

import nolambda.stream.cleaningservice.SearchPattern

class LayoutFileRemover : FileRemover(
    fileType = "layout",
    resourceName = "layout",
    type = SearchPattern.Type.LAYOUT
)