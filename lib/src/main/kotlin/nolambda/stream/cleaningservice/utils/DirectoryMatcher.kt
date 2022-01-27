package nolambda.stream.cleaningservice.utils

object DirectoryMatcher {

    fun matchLast(dirName: String, type: String): Boolean {
        return Regex(".*(${type}-.*\$)|.*(${type}\$)").matches(dirName)
    }
}
