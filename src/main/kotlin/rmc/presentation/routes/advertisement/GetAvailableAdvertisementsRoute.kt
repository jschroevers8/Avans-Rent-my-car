package rmc.presentation.routes.advertisement

import io.ktor.http.HttpStatusCode
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.route
import rmc.application.usecases.advertisement.GetAvailableAdvertisementsUsecase
import rmc.presentation.mappers.toResponse

fun Route.getAvailableAdvertisementsRoute(getAvailableAdvertisementsUsecase: GetAvailableAdvertisementsUsecase) {
    route("/advertisement") {
        get("/all") {
            val advertisements = getAvailableAdvertisementsUsecase.invoke()

            call.respond(HttpStatusCode.OK, advertisements.map { it.toResponse() })
        }
    }
}
