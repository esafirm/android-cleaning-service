package nolambda.stream.cleaningservice.components.file

import io.kotest.core.datatest.forAll
import io.kotest.core.spec.style.FunSpec
import io.kotest.data.row
import io.kotest.matchers.shouldBe

class MipmapRemoverSpec : FunSpec({

    val remover = MipmapFileRemover()

    context("It should match the pattern") {
        forAll(
            row("R.mipmap.ic_launcher", true),
            row("@mipmap/ic_launcher\"", true),
            row("@mipmap/ic_launcher<", true),
            row("R.drawable.ic_launch", false),
            row("@mipmap/ic_launcher_round\"", false)
        ) { (text, expected) ->

            remover.scanTargetFileTexts = text
            remover.checkTargetTextMatches("ic_launcher") shouldBe expected
        }
    }
})