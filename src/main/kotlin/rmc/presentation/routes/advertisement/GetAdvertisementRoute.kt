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
            val advertisementId = call.parameters["id"]?.toIntOrNull()

            if (advertisementId == null) {
                call.respond(HttpStatusCode.BadRequest, "Invalid or missing id parameter")
                return@get
            }

            val advertisement = getAdvertisementUsecase.invoke(advertisementId)
            if (advertisement == null) {
                call.respond(HttpStatusCode.NotFound, "Advertisement with id $advertisementId not found")
                return@get
            }

            call.respond(HttpStatusCode.OK, advertisement.toResponse())
        }
    }
}
