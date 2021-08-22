package maptoxml

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class XmlDslTest {

    private val languagesAndAuthors = mapOf("Java" to "Gosling", "Lisp" to "McCarthy", "Ruby" to "Matz")

    @Test
    fun testXmlFragment() {
        val xml = xml("languages") {
            languagesAndAuthors.entries.forEach { entry ->
                val (title, author) = entry
                node("language") {
                    attribute named "title" withValue title
                    node("author") {
                        attribute named "name" withValue author
                    }
                }
            }
        }

        assertThat(xml).isEqualTo("""
            <languages>
              <language title='Java'>
                <author name='Gosling'/>
              </language>
              <language title='Lisp'>
                <author name='McCarthy'/>
              </language>
              <language title='Ruby'>
                <author name='Matz'/>
              </language>
            </languages>
        """.trimIndent())
    }

}
