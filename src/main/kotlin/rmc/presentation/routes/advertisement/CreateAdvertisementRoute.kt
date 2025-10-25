package rmc.presentation.routes.advertisement

import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import rmc.application.usecases.advertisement.CreateAdvertisementUsecase
import rmc.infrastructure.plugins.userId
import rmc.presentation.dto.advertisement.CreateAdvertisement
import rmc.presentation.mappers.toResponse

fun Route.createAdvertisementRoute(createAdvertisementUsecase: CreateAdvertisementUsecase) {
    route("/advertisement") {
        post("/create") {
            val request = call.receive<CreateAdvertisement>()

            val created = createAdvertisementUsecase(request, call.userId)

            call.respond(HttpStatusCode.Created, created.toResponse())
        }
    }
}
