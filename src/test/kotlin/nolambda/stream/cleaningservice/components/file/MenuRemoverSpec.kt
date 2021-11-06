package nolambda.stream.cleaningservice.components.file

import io.kotest.core.datatest.forAll
import io.kotest.core.spec.style.FunSpec
import io.kotest.data.row
import io.kotest.matchers.shouldBe

class MenuRemoverSpec : FunSpec({

    val remover = MenuFileRemover()

    context("It should match the pattern") {
        forAll(
            row("R.menu.menu_main", true),
            row("@menu/menu_main\"", true),
            row("@menu/menu_main<", true),
            row("R.menu.menu_detail", false),
            row("@menu/menu_main2\"", false),
            row("@layout/menu_main", false)
        ) { (text, expected) ->

            remover.scanTargetFileTexts = text
            remover.checkTargetTextMatches("menu_main") shouldBe expected
        }
    }
})