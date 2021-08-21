package maptoxml

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class XmlDslTest {

    @Test
    fun testXmlFragment() {
        val xml = xml("languages") {
            languagesAndAuthors.entries.forEach { entry ->
                val (title, author) = entry
                tag("language") {
                    attribute named "title" withValue title
                    tag("author") {
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
