package rmc.presentation.routes.user

import io.ktor.http.HttpStatusCode
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.route
import rmc.application.usecases.user.LoginUsecase
import rmc.presentation.mappers.toResponse
import kotlin.text.toIntOrNull

fun Route.userLoginRoute(loginUsecase: LoginUsecase) {
    route("/login") {
        get("/{id}") {
            val id =
                call.parameters["id"]?.toIntOrNull()
                    ?: return@get call.respond(HttpStatusCode.BadRequest, "Invalid user ID")

            val user =
                loginUsecase.invoke(id)
                    ?: return@get call.respond(HttpStatusCode.NotFound, "User not found")

            call.respond(HttpStatusCode.OK, user.toResponse())
        }
    }
}
