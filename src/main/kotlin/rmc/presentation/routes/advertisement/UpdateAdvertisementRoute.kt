package rmc.presentation.routes.advertisement

import io.ktor.http.HttpStatusCode
import io.ktor.server.auth.authenticate
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.put
import io.ktor.server.routing.route
import rmc.application.usecases.advertisement.UpdateAdvertisementUsecase
import rmc.presentation.dto.advertisement.UpdateAdvertisement
import rmc.presentation.mappers.toResponse
import kotlin.text.toIntOrNull

fun Route.updateAdvertisementRoute(updateAdvertisementUsecase: UpdateAdvertisementUsecase) {
    authenticate("myAuth") {
        route("/advertisement") {
            put("/update/{id}") {
                val id = call.parameters["id"]?.toIntOrNull()
                if (id == null) {
                    call.respond(HttpStatusCode.BadRequest, "Invalid advertisement id")
                    return@put
                }

                val advertisementRequest = call.receive<UpdateAdvertisement>()
                val requestWithId = advertisementRequest.copy(carId = id)

                val updatedAdvertisement = updateAdvertisementUsecase(requestWithId)
                call.respond(HttpStatusCode.OK, updatedAdvertisement.toResponse())
            }
        }
    }
}
