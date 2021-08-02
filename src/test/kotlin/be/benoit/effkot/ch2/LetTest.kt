package be.benoit.effkot.ch2

import org.junit.jupiter.api.Test
import java.io.BufferedInputStream
import java.io.FileInputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream

class LetTest {

    @Test
    internal fun wrapObjectWithLet() {

        val zip = FileInputStream("src/test/resources/file.zip")
            .let { BufferedInputStream(it) }
            .let { ZipInputStream(it) }

        var entry: ZipEntry? = zip.nextEntry
        while (entry != null) {
            println(entry.name)
            entry = zip.nextEntry
        }

    }
}