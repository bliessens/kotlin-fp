plugins {
    kotlin("jvm") version "1.5.21"
//    kotlin("kapt") version "1.5.21"
    id("idea")
}

allprojects {

    apply(plugin = "kotlin")
    apply(plugin = "idea")

    repositories {
        mavenCentral()
    }

    dependencies {
        implementation(kotlin("stdlib"))
        implementation(kotlin("stdlib-jdk8"))

        implementation(ktor("client"))
        implementation(ktor("client-jetty"))
        implementation(ktor("client-json"))
        implementation(ktor("client-jackson"))
        implementation(ktor("client-auth-basic"))
        implementation(ktor("locations"))

        implementation(ktor("server-core"))
        implementation(ktor("server-netty"))
        runtimeOnly("ch.qos.logback:logback-classic:1.2.5")

        testImplementation(kotlin("reflect"))
        testImplementation(kotlin("test"))
        testImplementation("org.junit.jupiter:junit-jupiter:5.7.2")
        implementation("net.sf.jt400:jt400-jdk8:10.4")
        implementation("com.mchange:c3p0:0.9.5.5")
    }

    tasks.withType(org.jetbrains.kotlin.gradle.dsl.KotlinJvmCompile::class.java).all {
        kotlinOptions {
            jvmTarget = "11"
        }
    }

    tasks.test {
        useJUnitPlatform()
        testLogging {
            events(/*"passed", "skipped",*/ "failed")
        }
    }

    idea {
        module {
            outputDir = file("$rootDir/out/production/$name")
            testOutputDir = file("$rootDir/out/test/$name")

            excludeDirs = setOf(file(".gradle"), file(".idea"), file("build"), file("gradle"))

            isDownloadSources = true
            isDownloadJavadoc = true
        }
    }
}

fun DependencyHandlerScope.ktor(module: kotlin.String): String = "io.ktor:ktor-${module}:1.6.1"
