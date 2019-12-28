import org.junit.Test
import kotlin.test.assertEquals

internal class ScopesKtTest {

    @Test
    internal fun testApplyUsesABlock() {
        val buffer2 = StringBuilder("a").apply { append("b"); append("c") }

        assertEquals("abc", buffer2.toString())
    }

    @Test
    internal fun testAlsoUsesALambda() {
        val buffer = StringBuilder("a").also { sb -> sb.append("b") }

        assertEquals("ab", buffer.toString())
    }

    @Test
    internal fun testLet() {
        val buffer = "ab".let { str -> StringBuilder(str) }

        assertEquals("ab", buffer.toString())
    }

    @Test
    internal fun testWith() {
        val buffer = StringBuilder()
        with(buffer) { append("a"); append("b") }

        assertEquals("ab", buffer.toString())
    }
}

