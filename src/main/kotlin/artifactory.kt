package artifactory

import io.ktor.client.*
import io.ktor.client.features.json.*
import io.ktor.client.request.*
import kotlin.properties.Delegates

@DslMarker
annotation class ArtifactoryDsl

@ArtifactoryDsl
fun artifactory(init: ArtifactoryBuilder.() -> Unit): Artifactory {
    return ArtifactoryBuilder().apply { init() }.run { build() }
}

class ArtifactoryBuilder {
    internal var url: String by Delegates.notNull()
    internal var auth: Credentials = NoAuthentication
    internal var repositories: Collection<Repository> = listOf()

    fun url(block: () -> String): Unit {
        this.url = block()
    }

    fun auth(init: AuthenticationBuilder.() -> Unit): Unit {
        auth = AuthenticationBuilder().apply { init() }.run { build() }
    }

    fun repositories(init: RepositoryCollector.() -> Unit): Unit {
        repositories = RepositoryCollector().apply { init() }.run { build() }
    }

    fun build(): Artifactory {
        return Artifactory(url, auth, repositories)
    }
}

class RepositoryCollector {
    internal var repositories = listOf<Repository>()

    fun timeLimited(days: Int = 60, name: () -> String): Unit {
        repositories += Repository(name(), RepoType.DOCKER, TimeBasedExpiry(days))
    }

    fun quantityLimited(max: Int = 10, name: () -> String): Unit {
        repositories += Repository(name(), RepoType.DOCKER, QuantityBasedExpiry(max))
    }

//    operator fun invoke() : Collection<Repository> {
//        return repositories;
//    }

    fun build(): Collection<Repository> {
        return repositories
    }
}

@Deprecated("should be detected through Artifactory API")
enum class RepoType {
    RPM, DOCKER
}

sealed class Strategy
data class TimeBasedExpiry(val daysTilExpiry: Int) : Strategy()
data class QuantityBasedExpiry(val maxArtifactsToKeep: Int) : Strategy()

class Repository(val name: String, val type: RepoType, val strategy: Strategy)

sealed class Credentials
object NoAuthentication : Credentials()
class Authentication(val user: String, val password: String) : Credentials()


class AuthenticationBuilder {
    internal var user by Delegates.notNull<String>()
    internal var password by Delegates.notNull<String>()
    fun build(): Credentials {
        return Authentication(user, password)
    }
}

val client = HttpClient() {
    install(JsonFeature) {
        serializer = JacksonSerializer()
    }
//    install(Auth) {
//        basic {
//            username = ""
//            password = ""
//        }
//    }
}

class Artifactory(
    val baseUrl: String,
    val auth: Credentials,
    val repositories: Collection<Repository>
) {

}


suspend fun main(args: Array<String>) {
    val client = HttpClient() {
        install(JsonFeature) {
            serializer = JacksonSerializer()
        }
    }
    val response =
        client.get<String>("http://valartifactorydev01.violabor.local:8081/artifactory/repositories/docker-dev")
    println(response)

    client.close()
}
