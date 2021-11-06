package nolambda.stream.cleaningservice.utils

import java.io.BufferedInputStream
import java.io.BufferedOutputStream
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.FileWriter
import java.io.InputStreamReader
import java.util.zip.ZipEntry
import java.util.zip.ZipFile
import java.util.zip.ZipOutputStream


object ZipManager {

    fun zip(files: List<File>, zipFile: File) {
        val outputStream = ZipOutputStream(BufferedOutputStream(FileOutputStream(zipFile)))

        outputStream.use { output ->
            files.forEach { file ->
                if (file.length() > 1) {
                    FileInputStream(file).use { input ->
                        BufferedInputStream(input).use { origin ->
                            val entry = ZipEntry(file.name)
                            output.putNextEntry(entry)
                            origin.copyTo(output, 1024)
                        }
                    }
                }
            }
        }
    }

    //If we do not set encoding as "ISO-8859-1", European characters will be replaced with '?'.
    fun unzip(files: List<File>, zipFile: ZipFile) {
        zipFile.use { zip ->
            zip.entries().asSequence().forEach { entry ->
                zip.getInputStream(entry).use { input ->
                    BufferedReader(InputStreamReader(input, "ISO-8859-1")).use { reader ->
                        files.find { it.name.contains(entry.name) }?.run {
                            BufferedWriter(FileWriter(this)).use { writer ->
                                var line: String? = null
                                while (run {
                                        line = reader.readLine()
                                        line
                                    } != null) {
                                    writer.append(line).append('\n')
                                }
                                writer.flush()
                            }
                        }
                    }
                }
            }
        }
    }

    fun unzip(file: File, outputDir: File) {
        if (outputDir.exists().not()) {
            outputDir.mkdirs()
        }

        ZipFile(file).use { zip ->
            zip.entries().asSequence().forEach { entry ->
                zip.getInputStream(entry).use { input ->

                    val outputFile = File(outputDir, entry.name)

                    if (entry.isDirectory) {
                        outputFile.mkdirs()
                    } else {
                        val parent = outputFile.parentFile
                        if (parent.exists().not()) {
                            parent.mkdirs()
                        }

                        val outputStream = outputFile.outputStream()
                        outputStream.use { output -> input.copyTo(output) }
                    }
                }
            }
        }
    }
}
