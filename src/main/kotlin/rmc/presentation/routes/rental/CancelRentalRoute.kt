package rmc.presentation.routes.rental

import io.ktor.http.HttpStatusCode
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import rmc.application.usecases.rental.CancelRentalUsecase
import rmc.presentation.mappers.toResponse
import kotlin.text.toIntOrNull

fun Route.cancelRentalRoute(cancelRentalUsecase: CancelRentalUsecase) {
    route("/rental") {
        post("/cancel/{id}") {
            val rentalId = call.parameters["id"]?.toIntOrNull()

            if (rentalId == null) {
                call.respond(HttpStatusCode.BadRequest, "Invalid or missing id parameter")
                return@post
            }

            val rental = cancelRentalUsecase(rentalId)

            call.respond(HttpStatusCode.OK, rental.toResponse())
        }
    }
}
