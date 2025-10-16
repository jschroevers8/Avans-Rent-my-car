package rmc.presentation.routes.advertisement

import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import rmc.application.usecases.advertisement.CreateAdvertisementUsecase
import rmc.application.usecases.advertisement.GetAdvertisementUsecase
import rmc.application.usecases.advertisement.GetAvailableAdvertisementsUsecase
import rmc.application.usecases.car.CreateCarUsecase
import rmc.presentation.dto.advertisement.CreateAdvertisement
import rmc.presentation.dto.car.CreateCar
import rmc.presentation.mappers.toResponse
import kotlin.text.toIntOrNull

fun Route.getAvailableAdvertisementsRoute(getAvailableAdvertisementsUsecase: GetAvailableAdvertisementsUsecase) {
    route("/advertisement") {
        get("/all") {
            val advertisements = getAvailableAdvertisementsUsecase.invoke()

            call.respond(HttpStatusCode.OK, advertisements.map { it.toResponse() })
        }
    }
}
