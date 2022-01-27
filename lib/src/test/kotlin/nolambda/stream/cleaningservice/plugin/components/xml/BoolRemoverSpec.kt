package nolambda.stream.cleaningservice.plugin.components.xml

import io.kotest.core.datatest.forAll
import io.kotest.core.spec.style.FunSpec
import io.kotest.data.row
import io.kotest.matchers.shouldBe
import nolambda.stream.cleaningservice.remover.xml.BoolXmlRemover

class BoolRemoverSpec : FunSpec({

    val remover = BoolXmlRemover()

    context("It should remove valid elements") {
        forAll(
            row("R.bool.pref_value", true),
            row("@bool/pref_value\"", true),
            row("@bool/pref_value<", true),
            row("R.bool.pref", false),
            row("@bool/pref_value2\"", false),
            row("@bool/pref_value", false),
        ) { (text, expected) ->

            remover.scanTargetFileTexts = text
            remover.checkTargetTextMatches("pref_value") shouldBe expected
        }
    }

})
