package nolambda.stream.cleaningservice.plugin.components.file

import io.kotest.core.datatest.forAll
import io.kotest.core.spec.style.FunSpec
import io.kotest.data.row
import io.kotest.matchers.shouldBe
import nolambda.stream.cleaningservice.remover.file.ColorFileRemover

class ColorRemoverSpec : FunSpec({

    val remover = ColorFileRemover()

    context("It should match the pattern") {
        forAll(
            row("R.color.yellow", true),
            row("R.color.yellow)", true),
            row("R.color.yellow\"", true),
            row("@color/yellow\"", true),
            row("@color/yellow<", true),
            row("@color/yellow2", false),
            row("R.color.new_yellow", false),
            row("@column/yellow", false)
        ) { (text, expected) ->

            remover.scanTargetFileTexts = text
            remover.checkTargetTextMatches("yellow") shouldBe expected
        }
    }
})
