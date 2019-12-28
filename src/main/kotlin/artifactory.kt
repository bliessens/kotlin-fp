package artifactory

@DslMarker
annotation class ArtifactoryDsl

@ArtifactoryDsl
fun artifactory(init: ArtifactoryBuilder.() -> Unit): Artifactory {
    return ArtifactoryBuilder().apply { init() }.run { build() }
}

class ArtifactoryBuilder {
    internal var at = ""
    internal var auth: Credentials = NoAuthentication
    internal var repositories: Collection<Repository> = listOf()

    fun at(url: String): Unit {
        this.at = url
    }

    fun at(block: () -> String): Unit {
        this.at = block()
    }

    fun auth(init: AuthenticationBuilder.() -> Unit): Unit {
        auth = AuthenticationBuilder().apply { init() }.run { build() }
    }

    fun repo(init: RepositoryBuilder.() -> Unit): Unit {
        repositories += RepositoryBuilder().apply { init() }.run { build() }
    }

    infix fun String.type(aType: String): RepositoryBuilder {
        val builder = RepositoryBuilder()
        builder.name = this
        builder.type = RepoType.valueOf(aType.toUpperCase())
        return builder
    }

    infix fun RepositoryBuilder.cleanup(aStrategy: Strategy): Unit {
        this.cleanUp = aStrategy
        repositories += this.build()
    }

    fun build(): Artifactory {
        return Artifactory(at, auth, repositories)
    }
}

class RepositoryBuilder {
    lateinit var name: String
    lateinit var type: RepoType
    var cleanUp: Strategy = NoStrategy

    fun build(): Repository {
        assert(cleanUp != NoStrategy, { "Strategy should be set on repo '$name'" })
        return Repository(name, type, cleanUp)
    }
}

enum class RepoType {
    RPM, DOCKER
}

sealed class Strategy
object NoStrategy : Strategy()
class TimeBasedExpiry(daysTilExpiry: Int = 30) : Strategy()
class QuantityBasedExpiry(maxArtifactsToKeep: Int = 10) : Strategy()

class Repository(val name: String, val type: RepoType, val strategy: Strategy)

sealed class Credentials
object NoAuthentication : Credentials()
class Authentication(val user: String, val password: String) : Credentials()


class AuthenticationBuilder {
    var user = ""
    var password = ""
    fun build(): Credentials {
        return Authentication(user, password)
    }
}

class Artifactory(
    val baseUrl: String,
    val auth: Credentials,
    val repositories: Collection<Repository>
) {

}
