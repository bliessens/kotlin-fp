import artifactory.*
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.fail

internal class ArtifactoryBuilderTest {

    @Test
    internal fun testSyntax() {
        val artifactory = artifactory {
            at { "abc" }
            auth {
                user = "srvjenkins"
                password = ""
            }
            repo {
                name = "docker-dev"
                type = RepoType.DOCKER
                cleanUp = TimeBasedExpiry()
            }

            "docker-tst" type "docker" cleanup QuantityBasedExpiry()
        }

        assertEquals(artifactory.baseUrl, "abc")
        assertTrue(Authentication::class.isInstance(artifactory.auth))
        assertEquals(artifactory.repositories.size, 2)
        assertEquals(artifactory.repositories.last().name, "docker-tst")
        assertEquals(artifactory.repositories.last().type, RepoType.DOCKER)

    }

    @Test
    internal fun testDefaults() {
        val artifactory = artifactory {}

        assertEquals(artifactory.baseUrl, "")
        assertTrue(NoAuthentication::class.isInstance(artifactory.auth))
    }

    @Test
    internal fun failWhenNoStrategy() {
        try {
            artifactory {
                at { "abc" }
                repo {
                    name = "docker-dev"
                    type = RepoType.DOCKER
                }
            }
            fail("should fail because missing repo config")
        } catch (e: AssertionError) {

        }
    }

    @Test
    internal fun testAuthentication() {
        val artifactory = artifactory {
            at { "http://valartifatorydev01.violabor.local" }
            auth {
                user = "srvjenkins"
                password = ""
            }
        }

        assertTrue(Authentication::class.isInstance(artifactory.auth))
    }

    @Test
    internal fun testAtWithStringParam() {
        val artifactory = artifactory {
            at("http://valartifatorydev01.violabor.local")
        }
        assertEquals(artifactory.baseUrl, "http://valartifatorydev01.violabor.local")
    }

    @Test
    internal fun testAtWithLambda() {
        val artifactory = artifactory {
            at { "http://valartifatorydev01.violabor.local" }
        }
        assertEquals(artifactory.baseUrl, "http://valartifatorydev01.violabor.local")
    }

    @Test
    internal fun testAtAsProperty() {
        val artifactory = artifactory {
            at = "http://valartifatorydev01.violabor.local"
        }
        assertEquals(artifactory.baseUrl, "http://valartifatorydev01.violabor.local")
    }
}