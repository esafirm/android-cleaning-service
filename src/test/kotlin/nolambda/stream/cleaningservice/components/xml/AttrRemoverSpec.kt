package nolambda.stream.cleaningservice.components.xml

import io.kotest.core.datatest.forAll
import io.kotest.core.spec.style.FunSpec
import io.kotest.data.row
import io.kotest.matchers.shouldBe

class AttrRemoverSpec : FunSpec({

    val remover = AttrXmlRemover()

    context("It should remove valid elements") {
        forAll(
            row("R.styleable.CustomView", true),
            row("R.style.CustomView", false),
        ) { (text, expected) ->

            remover.scanTargetFileTexts = text
            remover.checkTargetTextMatches("CustomView") shouldBe expected
        }
    }

})