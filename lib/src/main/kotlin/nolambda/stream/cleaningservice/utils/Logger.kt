package nolambda.stream.cleaningservice.utils

class SimpleLogger : Logger {

    companion object {
        private const val ANSI_RESET = "\u001B[0m"
        private const val ANSI_GREEN = "\u001B[32m"
        private const val ANSI_YELLOW = "\u001B[33m"

        private const val IS_DEBUG = true
    }

    override fun logDev(text: String) {
        if (IS_DEBUG) {
            println("=> $text")
        }
    }

    override fun logGreen(text: String) {
        println("$ANSI_GREEN${text}$ANSI_RESET")
    }

    override fun logYellow(text: String) {
        println("$ANSI_YELLOW${text}$ANSI_RESET")
    }

    override fun log(text: String) {
        println(text)
    }
}

object NoopLogger : Logger {
    override fun logDev(text: String) {}

    override fun logGreen(text: String) {}

    override fun logYellow(text: String) {}

    override fun log(text: String) {}
}

interface Logger {
    fun logDev(text: String)
    fun logGreen(text: String)
    fun logYellow(text: String)
    fun log(text: String)
}
