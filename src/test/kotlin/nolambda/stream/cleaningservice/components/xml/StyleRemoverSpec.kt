package nolambda.stream.cleaningservice.components.xml

import io.kotest.core.datatest.forAll
import io.kotest.core.spec.style.FunSpec
import io.kotest.data.row
import io.kotest.matchers.shouldBe

class StyleRemoverSpec : FunSpec({

    val remover = StyleXmlRemover()

    context("It should remove valid elements") {
        forAll(
            row("R.style.TitleTextAppearance", true),
            row("@style/TitleTextAppearance\"", true),
            row("@style/TitleTextAppearance<", true),
            row("parent=\"TitleTextAppearance\"", true),
            row("@style/TitleTextAppearance.", true),
            row("R.style.TitleTextAppear", false),
            row("@style/TitleTextAppearance2\"", false),
            row("@theme/TitleTextAppearance", false),
        ) { (text, expected) ->

            remover.scanTargetFileTexts = text
            remover.checkTargetTextMatches("TitleTextAppearance") shouldBe expected
        }
    }

})