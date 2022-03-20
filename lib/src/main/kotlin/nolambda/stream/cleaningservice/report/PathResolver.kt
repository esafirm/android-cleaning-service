package nolambda.stream.cleaningservice.report

import java.io.File

interface PathResolver {
    fun resolve(removedFile: File): String

    object Simple : PathResolver {
        override fun resolve(removedFile: File): String {
            return removedFile.path
        }
    }

    class RelativeToParent(
        private val parent: File
    ) : PathResolver {
        override fun resolve(removedFile: File): String {
            return removedFile.relativeTo(parent).path
        }
    }
}
