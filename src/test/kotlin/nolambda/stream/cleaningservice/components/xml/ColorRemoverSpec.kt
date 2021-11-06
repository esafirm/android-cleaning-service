package nolambda.stream.cleaningservice.components.xml

import io.kotest.core.datatest.forAll
import io.kotest.core.spec.style.FunSpec
import io.kotest.data.row
import io.kotest.matchers.shouldBe

class ColorRemoverSpec : FunSpec({

    val remover = ColorXmlRemover()

    context("It should remove valid elements") {
        forAll(
            row("R.color.primary", true),
            row("@color/primary\"", true),
            row("@color/primary<", true),
            row("@color/primary:", true),
            row("@color/primary", false),
            row("@colorStateList/primary\"", true),
            row("R.color.secondary", false),
            row("@color/primary2\"", false),
            row("@style/primary", false),
        ) { (text, expected) ->

            remover.scanTargetFileTexts = text
            remover.checkTargetTextMatches("primary") shouldBe expected

        }
    }

})