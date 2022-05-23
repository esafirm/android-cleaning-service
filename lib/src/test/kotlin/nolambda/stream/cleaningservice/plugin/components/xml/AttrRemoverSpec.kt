package nolambda.stream.cleaningservice.plugin.components.xml

import io.kotest.core.datatest.forAll
import io.kotest.core.spec.style.FunSpec
import io.kotest.data.row
import io.kotest.matchers.shouldBe
import nolambda.stream.cleaningservice.remover.xml.AttrXmlRemover

class AttrRemoverSpec : FunSpec({

    val remover = AttrXmlRemover()

    context("It should remove valid elements") {
        forAll(
            row("R.styleable.CustomView", true),
            row("R.styleable.CustomView ", true),
            row("R.styleable.CustomView_", false),
            row("styleable.CustomView", true),
            row("styleable.CustomView_", false),
            row("R.style.CustomView", false),
        ) { (text, expected) ->

            remover.scanTargetFileTexts = text
            remover.checkTargetTextMatches("CustomView") shouldBe expected
        }
    }

})
