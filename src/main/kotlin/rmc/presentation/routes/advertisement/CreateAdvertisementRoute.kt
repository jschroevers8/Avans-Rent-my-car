package rmc.presentation.routes.advertisement

import io.ktor.http.HttpStatusCode
import io.ktor.server.auth.UserIdPrincipal
import io.ktor.server.auth.authenticate
import io.ktor.server.auth.principal
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import rmc.application.usecases.advertisement.CreateAdvertisementUsecase
import rmc.presentation.dto.advertisement.CreateAdvertisement
import rmc.presentation.mappers.toResponse

fun Route.createAdvertisementRoute(createAdvertisementUsecase: CreateAdvertisementUsecase) {
    authenticate("myAuth") {
        route("/advertisement") {
            post("/create") {
                val userId = call.principal<UserIdPrincipal>()?.name?.toIntOrNull()
                if (userId == null) {
                    call.respond(HttpStatusCode.BadRequest, "Invalid userId in token")
                    return@post
                }

                val request = call.receive<CreateAdvertisement>()

                val created = createAdvertisementUsecase(request, userId)

                call.respond(HttpStatusCode.Created, created.toResponse())
            }
        }
    }
}
