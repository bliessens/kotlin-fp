dependencies {
    implementation(exposed("core"))
    implementation(exposed("dao"))
    implementation(exposed("jdbc"))
    runtimeOnly("org.hsqldb:hsqldb:2.6.0")
}

fun exposed(module: String) = "org.jetbrains.exposed:exposed-${module}:0.32.1"

idea {
    module {
        excludeDirs = setOf(file("src/db"))
    }
}
