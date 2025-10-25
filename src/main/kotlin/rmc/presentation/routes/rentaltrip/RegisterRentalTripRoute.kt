package rmc.presentation.routes.rentaltrip

import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import rmc.application.usecases.rentaltrip.RegisterRentalTripUsecase
import rmc.presentation.dto.rentaltrip.RegisterRentalTrip
import rmc.presentation.mappers.toResponse

fun Route.registerRentalTripRoute(registerRentalTripUsecase: RegisterRentalTripUsecase) {
    route("/rentalTrip") {
        post("/register") {
            val request = call.receive<RegisterRentalTrip>()

            val trip = registerRentalTripUsecase(request)

            call.respond(HttpStatusCode.Created, trip.toResponse())
        }
    }
}
