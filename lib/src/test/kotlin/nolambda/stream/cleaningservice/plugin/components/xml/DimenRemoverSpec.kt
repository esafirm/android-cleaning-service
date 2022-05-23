package nolambda.stream.cleaningservice.plugin.components.xml

import io.kotest.core.datatest.forAll
import io.kotest.core.spec.style.FunSpec
import io.kotest.data.row
import io.kotest.matchers.shouldBe
import nolambda.stream.cleaningservice.remover.xml.DimenXmlRemover

class DimenRemoverSpec : FunSpec({

    val remover = DimenXmlRemover()

    context("It should remove valid elements") {
        forAll(
            row("R.dimen.text_medium", true),
            row("R.dimen.text_medium_", false),
            row("dimen.text_medium", true),
            row("dimen.text_medium_", false),
            row("dimen.text_medium ", true),
            row("dimen.text_medium)", true),
            row("@dimen/text_medium\"", true),
            row("@dimen/text_medium<", true),
            row("R.dimen.text", false),
            row("@dimen/text_medium2\"", false),
            row("@style/text_medium", false),
        ) { (text, expected) ->

            remover.scanTargetFileTexts = text
            remover.checkTargetTextMatches("text_medium") shouldBe expected
        }
    }

})
