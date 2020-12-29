import io.ktor.application.*
import io.ktor.locations.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main(args: Array<String>) {
    val server = embeddedServer(Netty, 8080, module = Application::myModule).start(wait = true)
}

fun Application.myModule() {
    install(Locations)
    routing {
//        get<RepoDefinitionRequest> { repo ->
//            call.respond( RepoDefinitionResponse(repo.,repo.is))
//        }
    }
}

@Location("/artifactory/api/repositories/{key}")
data class RepoDefinitionRequest(val key: String) {
    fun isDockerRepo(): Boolean {
        return key.contains("docker", true)
    }
}

data class RepoDefinitionResponse(val key: String, val packageType: String, val description: String, val rclass: String)

fun Routing.repositories() {
//    get<RepoDefinitionRequest> { repo ->
//        call.respond(HttpStatusCode.OK, RepoDefinitionResponse(,))
//    }
}

