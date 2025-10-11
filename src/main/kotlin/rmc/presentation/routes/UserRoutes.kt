package rmc.presentation.routes

import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import rmc.presentation.controllers.UserController
import rmc.presentation.dto.user.CreateUser

fun Route.userRoutes(userController: UserController) {

    route("/users") {

        post {
            val request = call.receive<CreateUser>()
            val created = userController.createUser(request)
            call.respond(HttpStatusCode.Created, created)
        }

        get("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
                ?: return@get call.respond(HttpStatusCode.BadRequest, "Invalid user ID")

            val user = userController.getUser(id)
                ?: return@get call.respond(HttpStatusCode.NotFound, "User not found")

            call.respond(HttpStatusCode.OK, user)
        }
    }
}