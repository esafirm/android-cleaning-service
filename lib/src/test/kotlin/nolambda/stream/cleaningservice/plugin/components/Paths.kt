package nolambda.stream.cleaningservice.plugin.components

import java.io.File

fun projectRootDir() = File(System.getProperty("user.dir")).parent

fun projectRootDirFile() = File(projectRootDir())
