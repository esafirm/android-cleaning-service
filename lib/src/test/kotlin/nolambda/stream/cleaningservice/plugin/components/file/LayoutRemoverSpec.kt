package nolambda.stream.cleaningservice.plugin.components.file

import io.kotest.core.datatest.forAll
import io.kotest.core.spec.style.FunSpec
import io.kotest.data.row
import io.kotest.matchers.shouldBe
import nolambda.stream.cleaningservice.remover.file.LayoutFileRemover

class LayoutRemoverSpec : FunSpec({

    val remover = LayoutFileRemover()

    context("It should match the pattern") {
        forAll(
            row("R.layout.activity_main", true),
            row(".activity_main.", true),
            row("@layout/activity_main\"", true),
            row("@layout/activity_main<", true),
            row("ActivityMainBinding", true),
            row("R.layout.fragment_main", false),
            row("@layout/activity_main2\"", false),
            row("@menu/activity_main", false),
        ) { (text, expected) ->

            remover.scanTargetFileTexts = text
            remover.checkTargetTextMatches("activity_main") shouldBe expected
        }
    }
})
