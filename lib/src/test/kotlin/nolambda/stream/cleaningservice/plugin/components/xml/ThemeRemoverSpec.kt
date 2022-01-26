package nolambda.stream.cleaningservice.plugin.components.xml

import io.kotest.core.datatest.forAll
import io.kotest.core.spec.style.FunSpec
import io.kotest.data.row
import io.kotest.matchers.shouldBe
import nolambda.stream.cleaningservice.components.xml.ThemeXmlRemover

class ThemeRemoverSpec : FunSpec({

    val remover = ThemeXmlRemover()

    context("It should remove valid elements") {
        forAll(
            row("R.style.AppTheme_Translucent", true),
            row("@style/AppTheme.Translucent\"", true),
            row("@style/AppTheme.Translucent<", true),
            row("parent=\"AppTheme.Translucent\"", true),
            row("@style/AppTheme.Translucent.", true),
            row("R.style.AppTheme.Trans", false),
            row("@style/AppTheme.Translucent2\"", false),
        ) { (fileName, expected) ->

            remover.scanTargetFileTexts = fileName
            remover.checkTargetTextMatches("AppTheme.Translucent") shouldBe expected
        }
    }

})
