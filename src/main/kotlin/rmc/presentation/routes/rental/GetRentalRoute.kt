package rmc.presentation.routes.rental

import io.ktor.http.HttpStatusCode
import io.ktor.server.auth.authenticate
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.route
import rmc.application.usecases.rental.GetRentalUsecase
import rmc.presentation.mappers.toResponse
import kotlin.text.toIntOrNull

fun Route.getRentalRoute(getRentalUsecase: GetRentalUsecase) {
    authenticate("myAuth") {
        route("/rental") {
            get("/show/{id}") {
                val rentalId = call.parameters["id"]?.toIntOrNull()

                if (rentalId == null) {
                    call.respond(HttpStatusCode.BadRequest, "Invalid or missing id parameter")
                    return@get
                }

                val rental = getRentalUsecase(rentalId)
                if (rental == null) {
                    call.respond(HttpStatusCode.NotFound, "Rental with id $rentalId not found")
                    return@get
                }

                call.respond(HttpStatusCode.OK, rental.toResponse())
            }
        }
    }
}
