package stack

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class ArrayListStackTest {

    @Test
    internal fun popEmptyStackReturnsNull() {
        val s = stackOf<String>()

        assertTrue(s.isEmpty())
        assertNull(s.pop())
    }

    @Test
    internal fun pushItemToStack() {
        val s = stackOf<String>()

        val pushed = s.push("abc")

        assertEquals("abc", pushed)
        assertEquals(1, s.size())
        assertFalse(s.isEmpty())
    }

    @Test
    internal fun popPushedItem() {
        val s = stackOf("d")

        assertEquals("d", s.pop())
        assertEquals(0, s.size())
        assertTrue(s.isEmpty())
    }

    @Test
    internal fun peekDoesNotRemoveItem() {
        val s = stackOf<String>()

        s.push("abc")

        assertEquals("abc", s.peek())
        assertEquals(1, s.size())
    }

    @Test
    internal fun pushMaintainsOrder() {
        val s = stackOf("abc", "def", "klm")

        assertEquals(3, s.size())
        assertEquals("klm", s.pop())
        assertEquals("def", s.pop())
        assertEquals("abc", s.pop())
    }

    private fun String.checkParenthesis(): Boolean {
        val stack = stackOf<Char>()

        fun popIfChar(char: Char): Boolean {
            if (stack.isEmpty())
                return true
            else {
                if (char == stack.peek())
                    stack.pop()
            }
            return false
        }

        this.forEach { char ->
            when (char) {
                '(', '{' -> stack.push(char)
                ')' -> if (popIfChar('(')) return false
                '}' -> if (popIfChar('{')) return false
            }
        }

        return stack.isEmpty()
    }

    @Test
    fun testStack() {
        assertTrue("((sdsd))".checkParenthesis())
        assertFalse("(()))".checkParenthesis())
        assertTrue("(()())".checkParenthesis())
    }

    @Test
    internal fun listToStackConversion() {
        val stack = listOf("(", "(", ")", "(", ")", ")").toStack()

        assertEquals(6, stack.size())
    }

    @Test
    internal fun mapToStackConversion() {
        val stack = mapOf(1 to "a", 2 to "b").toStack()

        assertEquals(2, stack.size())
        assertEquals(2 to "b", stack.pop())
        assertEquals(1 to "a", stack.pop())
    }

}
