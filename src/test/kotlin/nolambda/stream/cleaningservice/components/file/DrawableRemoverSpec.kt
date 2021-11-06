package nolambda.stream.cleaningservice.components.file

import io.kotest.core.datatest.forAll
import io.kotest.core.spec.style.FunSpec
import io.kotest.data.row
import io.kotest.matchers.shouldBe

class DrawableRemoverSpec : FunSpec({

    val remover = DrawableFileRemover()

    context("It should match the pattern") {
        forAll(
            row("ic_settings", "R.drawable.ic_settings", true),
            row("ic_settings", "@drawable/ic_settings\"", true),
            row("ic_settings", "@drawable/ic_settings<", true),
            row("img_balloon.9", "@drawable/img_balloon\"", true),
            row("img_balloon.9", "@drawable/img_balloon2\"", false),
            row("ic_settings", "R.drawable.ic_setting", false),
            row("ic_settings", "@mipmap/ic_settings", false)
        ) { (fileName, fileText, expected) ->

            remover.scanTargetFileTexts = fileText
            remover.checkTargetTextMatches(fileName) shouldBe expected
        }
    }
})