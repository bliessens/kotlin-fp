package artifactory

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.Ignore
import kotlin.test.assertEquals
import kotlin.test.assertTrue

internal class ArtifactoryBuilderTest {

    @Test
    internal fun testSyntax() {
        val artifactory = artifactory {
            url { "abc" }
            auth {
                user = "srvjenkins"
                password = ""
            }

            repositories {
                timeLimited { "docker-dev" }
                timeLimited(30) { "docker-tst" }
                quantityLimited { "docker-qas" }
            }

        }

        assertEquals(artifactory.baseUrl, "abc")
        assertTrue(Authentication::class.isInstance(artifactory.auth))
        assertEquals(artifactory.repositories.size, 3)
        assertEquals(artifactory.repositories.map { r -> r.name }, listOf("docker-dev", "docker-tst", "docker-qas"))
        assertEquals(
            artifactory.repositories.map { r -> r.type },
            listOf(RepoType.DOCKER, RepoType.DOCKER, RepoType.DOCKER)
        )
        assertEquals(
            artifactory.repositories.map { r -> r.strategy },
            listOf(TimeBasedExpiry(60), TimeBasedExpiry(30), QuantityBasedExpiry(10))
        )

    }

    @Test
    internal fun testBaseUrlIsMandatory() {
        assertThrows<IllegalStateException> { artifactory {} }
    }

    @Test
    internal fun testDefaultCredentials() {
        val artifactory = artifactory {
            url { "abc" }
        }
        assertEquals(artifactory.baseUrl, "abc")
        assertTrue(NoAuthentication::class.isInstance(artifactory.auth))
    }

    @Ignore
    @Test
    internal fun failWhenNoRepositoryName() {
        assertThrows<java.lang.IllegalStateException>("repo name is mandatory") {
            artifactory {
                url { "abc" }
                repositories {
                    timeLimited { "" }
                }
            }
        }
    }

    @Test
    internal fun testAuthentication() {
        val artifactory = artifactory {
            url { "http://valartifatorydev01.violabor.local" }
            auth {
                user = "srvjenkins"
                password = ""
            }
        }

        assertTrue(Authentication::class.isInstance(artifactory.auth))
    }

    @Test
    internal fun testAtWithLambda() {
        val artifactory = artifactory {
            url { "http://valartifatorydev01.violabor.local" }
        }
        assertEquals(artifactory.baseUrl, "http://valartifatorydev01.violabor.local")
    }

    @Test
    internal fun testAtAsProperty() {
        val artifactory = artifactory {
            url = "http://valartifatorydev01.violabor.local"
        }
        assertEquals(artifactory.baseUrl, "http://valartifatorydev01.violabor.local")
    }

}