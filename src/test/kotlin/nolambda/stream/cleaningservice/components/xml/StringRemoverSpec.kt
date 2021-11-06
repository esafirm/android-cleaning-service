package nolambda.stream.cleaningservice.components.xml

import io.kotest.core.datatest.forAll
import io.kotest.core.spec.style.FunSpec
import io.kotest.data.row
import io.kotest.matchers.shouldBe

class StringRemoverSpec : FunSpec({

    val remover = StringXmlRemover()

    context("It should remove valid elements") {
        forAll(
            row("R.string.app_name", true),
            row("@string/app_name\"", true),
            row("@string/app_name<", true),
            row("@string/app_name(", true),
            row("@string/app_name)", true),
            row("@string/app_name}", true),
            row("@string/app_name:", true),
            row("@string/app_name ", true),
            row("@string/app_name\n", true),
            row("@string/app_name", false),
            row("R.string.app", false),
            row("@string/app_name2\"", false),
            row("@style/app_name", false)
        ) { (text, expected) ->

            remover.scanTargetFileTexts = text
            remover.checkTargetTextMatches("app_name") shouldBe expected
        }
    }

})