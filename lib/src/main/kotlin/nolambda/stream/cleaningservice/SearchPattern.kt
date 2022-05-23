package nolambda.stream.cleaningservice

class SearchPattern {

    companion object {
        fun from(type: String): Type {
            return when (type) {
                "style" -> Type.STYLE
                "drawable" -> Type.DRAWABLE
                "layout" -> Type.LAYOUT
                else -> Type.DEFAULT
            }
        }

        private fun toCamelCaseWithUnderscore(name: String): String {
            return name.replace(Regex("(\\.)([A-Za-z0-9])")) {
                "_${it.value[1].toString().uppercase()}"
            }
        }

        @Suppress("DEPRECATION")
        private fun toCamelCase(text: String): String {
            return text.replace(Regex("(_)([A-Za-z0-9])")) {
                it.value[1].toString().toUpperCase()
            }.capitalize()
        }

        /**
         * @return pattern for R class that used in Kotlin or Java file
         */
        private fun resPattern(resourceName: String, target: String): String {
            return "(${resourceName}\\.${target}(?![a-zA-Z_]))"
        }

        fun create(resourceName: String, target: String, type: Type = Type.DEFAULT): Regex {
            val pattern = when (type) {
                Type.STYLE -> {
                    val t = toCamelCaseWithUnderscore(target)
                    "(@(${resourceName}|${resourceName}StateList)\\/${target}[\\s!\"#\\\$%&'()\\*\\+\\-\\,\\\\/:;<=>?@\\[\\\\\\]^`{|}~])|${resPattern(resourceName, t)}|(${target}\\.)|(parent=\"${target}\")"
                }
                Type.DRAWABLE -> {
                    val t = target.removeSuffix(".9")
                    "(@(${resourceName}|${resourceName}StateList)\\/${t}[\\s!\"#\\\$%&'()\\*\\+\\-\\,\\\\\\/:;<=>?@\\[\\\\\\]^`{|}~])|${resPattern(resourceName, t)}"
                }
                Type.LAYOUT -> {
                    val t = toCamelCase(target)
                    "(@(${resourceName}|${resourceName}StateList)\\/${target}[\\s!\"#\\\$%&'()\\*\\+\\-\\,\\\\\\/:;<=>?@\\[\\\\\\]`{|}~])|${resPattern(resourceName, target)}|(${t}Binding)|(\\.${target}\\.)"
                }
                Type.DEFAULT -> {
                    "(@(${resourceName}|${resourceName}StateList)\\/${target}[\\s!\"#\\\$%&'()\\*\\+\\-\\,\\\\\\/:;<=>?@\\[\\\\\\]`{|}~])|${resPattern(resourceName, target)}"
                }
            }
            return Regex(pattern)
        }

    }

    enum class Type {
        STYLE,
        DRAWABLE,
        LAYOUT,
        DEFAULT
    }
}
