package nolambda.stream.cleaningservice.plugin.components.file

import io.kotest.core.datatest.forAll
import io.kotest.core.spec.style.FunSpec
import io.kotest.data.row
import io.kotest.matchers.shouldBe
import nolambda.stream.cleaningservice.remover.file.AnimatorFileRemover

class AnimatorRemoverSpec : FunSpec({

    val remover = AnimatorFileRemover()

    context("It should match the pattern") {
        forAll(
            row("R.animator.fade_animation", true),
            row("R.animator.fade_animation)", true),
            row("R.animator.fade_animation\"", true),
            row("@animator/fade_animation\"", true),
            row("@animator/fade_animation<", true),
            row("@animator/fade_animation2", false),
            row("R.animator.scale_animation", false),
            row("@anim/fade_animation", false)
        ) { (text, expected) ->

            remover.scanTargetFileTexts = text
            remover.checkTargetTextMatches("fade_animation") shouldBe expected
        }
    }
})
