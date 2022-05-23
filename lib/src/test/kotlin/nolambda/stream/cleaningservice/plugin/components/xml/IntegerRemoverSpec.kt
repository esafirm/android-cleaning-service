package nolambda.stream.cleaningservice.plugin.components.xml

import io.kotest.core.datatest.forAll
import io.kotest.core.spec.style.FunSpec
import io.kotest.data.row
import io.kotest.matchers.shouldBe
import nolambda.stream.cleaningservice.remover.xml.IntegerXmlRemover

class IntegerRemoverSpec : FunSpec({

    val remover = IntegerXmlRemover()

    context("It should remove valid elements") {
        forAll(
            row("R.integer.max_length", true),
            row("R.integer.max_length_", false),
            row("integer.max_length", true),
            row("integer.max_length ", true),
            row("integer.max_length)", false),
            row("string.app_name_", false),
            row("@integer/max_length\"", true),
            row("@integer/max_length<", true),
            row("R.integer.max", false),
            row("@integer/max_length2\"", false),
            row("@integer/max_length", false),
        ) { (text, expected) ->

            remover.scanTargetFileTexts = text
            remover.checkTargetTextMatches("max_length") shouldBe expected
        }
    }

})
