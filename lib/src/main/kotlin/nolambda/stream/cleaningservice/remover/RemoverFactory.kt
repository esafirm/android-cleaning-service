package nolambda.stream.cleaningservice.remover

import nolambda.stream.cleaningservice.remover.file.AnimFileRemover
import nolambda.stream.cleaningservice.remover.file.AnimatorFileRemover
import nolambda.stream.cleaningservice.remover.file.ColorFileRemover
import nolambda.stream.cleaningservice.remover.file.DrawableFileRemover
import nolambda.stream.cleaningservice.remover.file.LayoutFileRemover
import nolambda.stream.cleaningservice.remover.file.MenuFileRemover
import nolambda.stream.cleaningservice.remover.file.MipmapFileRemover
import nolambda.stream.cleaningservice.remover.xml.AttrXmlRemover
import nolambda.stream.cleaningservice.remover.xml.BoolXmlRemover
import nolambda.stream.cleaningservice.remover.xml.ColorXmlRemover
import nolambda.stream.cleaningservice.remover.xml.DimenXmlRemover
import nolambda.stream.cleaningservice.remover.xml.IdXmlRemover
import nolambda.stream.cleaningservice.remover.xml.IntegerXmlRemover
import nolambda.stream.cleaningservice.remover.xml.StringXmlRemover
import nolambda.stream.cleaningservice.remover.xml.StyleXmlRemover
import nolambda.stream.cleaningservice.remover.xml.ThemeXmlRemover

object RemoverFactory {

    /**
     * Return all available removers
     */
    fun getAllRemovers(): List<AbstractRemover> {
        return getFileRemovers() + getXmlRemovers()
    }

    /**
     * Return all remover that extends [nolambda.stream.cleaningservice.remover.xml.XmlValueRemover]
     */
    fun getXmlRemovers(): List<AbstractRemover> {
        return listOf(
            AttrXmlRemover(),
            BoolXmlRemover(),
            ColorXmlRemover(),
            DimenXmlRemover(),
            IdXmlRemover(),
            IntegerXmlRemover(),
            StringXmlRemover(),
            StyleXmlRemover(),
            ThemeXmlRemover(),
        )
    }

    /**
     * Return all remover that extend [nolambda.stream.cleaningservice.remover.file.FileRemover]
     */
    fun getFileRemovers(): List<AbstractRemover> {
        return listOf(
            AnimatorFileRemover(),
            AnimFileRemover(),
            ColorFileRemover(),
            DrawableFileRemover(),
            LayoutFileRemover(),
            MenuFileRemover(),
            MipmapFileRemover(),
        )
    }

    fun getEssentialRemover(): List<AbstractRemover> {
        return listOf(
            LayoutFileRemover(),
            DrawableFileRemover(),
            StringXmlRemover(),
            StyleXmlRemover()
        )
    }
}
