package rmc.presentation.routes.user

import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import rmc.application.usecases.user.SignupUsecase
import rmc.presentation.dto.user.CreateUser
import rmc.presentation.mappers.toResponse

fun Route.userSignupRoute(signupUsecase: SignupUsecase) {
    route("/signup") {
        post {
            val request = call.receive<CreateUser>()

            val created = signupUsecase(request)

            call.respond(HttpStatusCode.Created, created.toResponse())
        }
    }
}
