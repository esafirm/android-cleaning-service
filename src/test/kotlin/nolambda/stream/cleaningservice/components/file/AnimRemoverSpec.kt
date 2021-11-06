package nolambda.stream.cleaningservice.components.file

import io.kotest.core.datatest.forAll
import io.kotest.core.spec.style.FunSpec
import io.kotest.data.row
import io.kotest.matchers.shouldBe

class AnimRemoverSpec : FunSpec({

    val remover = AnimFileRemover()

    context("It should match the pattern") {
        forAll(
            row("R.anim.fade_transition", true),
            row("R.anim.fade_transition)", true),
            row("R.anim.fade_transition\"", true),
            row("@anim/fade_transition\"", true),
            row("@anim/fade_transition<", true),
            row("R.animator.fade_transition\"", false),
            row("@anim/fade_transition2", false),
            row("R.anim.scale_transition", false),
            row("@animator/fade_transition", false)
        ) { (text, expected) ->

            remover.scanTargetFileTexts = text
            remover.checkTargetTextMatches("fade_transition") shouldBe expected
        }
    }
})