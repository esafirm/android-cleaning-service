package nolambda.stream.cleaningservice.plugin.components.xml

import io.kotest.core.datatest.forAll
import io.kotest.core.spec.style.FunSpec
import io.kotest.data.row
import io.kotest.matchers.shouldBe
import nolambda.stream.cleaningservice.components.xml.IdXmlRemover

class IdRemoverSpec : FunSpec({

    val remover = IdXmlRemover()

    context("It should remove valid elements") {
        forAll(
            row("R.id.view_id", true),
            row("@id/view_id\"", true),
            row("@id/view_id<", true),
            row("R.id.view", false),
            row("@id/view_id2\"", false),
        ) { (text, expected) ->

            remover.scanTargetFileTexts = text
            remover.checkTargetTextMatches("view_id") shouldBe expected
        }
    }

})
