package nolambda.stream.cleaningservice.remover.file

import nolambda.stream.cleaningservice.SearchPattern

class DrawableFileRemover : FileRemover(
    fileType = "drawable",
    resourceName = "drawable",
    type = SearchPattern.Type.DRAWABLE
)
