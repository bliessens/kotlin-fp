package be.benoit.artifactory

@DslMarker
annotation class ArtifactoryDsl

@ArtifactoryDsl
fun artifactory(init: ArtifactoryBuilder.() -> Unit): Artifactory {
    return ArtifactoryBuilder().apply { init }.run { build() }
}


class Artifactory
class ArtifactoryBuilder {

    fun build(): Artifactory {
        return Artifactory()
    }
}
