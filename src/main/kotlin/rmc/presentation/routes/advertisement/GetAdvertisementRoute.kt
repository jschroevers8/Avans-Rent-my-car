package rmc.presentation.routes.advertisement

import io.ktor.http.HttpStatusCode
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.route
import rmc.application.usecases.advertisement.GetAdvertisementUsecase
import rmc.presentation.mappers.toResponse
import kotlin.text.toIntOrNull

fun Route.getAdvertisementRoute(getAdvertisementUsecase: GetAdvertisementUsecase) {
    route("/advertisement") {
        get("/show/{id}") {
            val advertisementId =
                call.parameters["id"]?.toIntOrNull()
                    ?: return@get call.respond(HttpStatusCode.BadRequest, "Invalid or missing id parameter")

            val advertisement = getAdvertisementUsecase(advertisementId)

            call.respond(HttpStatusCode.OK, advertisement.toResponse())
        }
    }
}
