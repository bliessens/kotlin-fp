package be.benoit.artifactory

@DslMarker
annotation class ArtifactoryDsl

@ArtifactoryDsl
fun artifactory(init: ArtifactoryBuilder.() -> Unit): Artifactory {
    return ArtifactoryBuilder().apply { init() }.run { build() }
}

@ArtifactoryDsl
abstract class Named {
    var name = ""
    operator fun String.unaryPlus() {
        name = this
    }
}

class Artifactory(val name: String, val repositories: List<Repository>)


class ArtifactoryBuilder : Named() {
    private val repositories = mutableListOf<Repository>()

    fun build(): Artifactory {
        return Artifactory(name, repositories)
    }

    fun repository(init: RepositoryBuilder.() -> Unit): ArtifactoryBuilder {
        repositories.add(RepositoryBuilder().apply(init).build())
        return this
    }
}

class Repository(val name: String)

class RepositoryBuilder : Named() {

    fun build(): Repository {
        return Repository(name)
    }
}

