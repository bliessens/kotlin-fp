package be.benoit.artifactory

import com.example.html.html
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

internal class ArtifactoryTest {

    @Test
    internal fun name() {
        val server = artifactory {
            +"Artifactory Server Description"

            repository {
                +"My Repo"
            }
            repository {
                +"Other Repo"
            }
        }

        assertNotNull(server)
        assertEquals("Artifactory Server Description", server.name)
        assertTrue("No repositories") { server.repositories.size == 2 }
        assertEquals("My Repo", server.repositories.first().name)
        assertEquals("Other Repo", server.repositories.get(1).name)
    }

    @Test
    internal fun testHtml() {
        val h = html {
            head {
                title {
                    +"ok"
                }
            }
            body {

            }
        }

        assertNotNull(h.children)
        assertEquals(2, h.children.size)
    }
}