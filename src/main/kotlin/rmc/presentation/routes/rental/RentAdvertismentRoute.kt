package rmc.presentation.routes.rental

import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import rmc.application.usecases.rental.RentAdvertisementUsecase
import rmc.infrastructure.plugins.userId
import rmc.presentation.dto.rental.RentAdvertisement
import rmc.presentation.mappers.toResponse

fun Route.rentAdvertisementRoute(rentAdvertisementUsecase: RentAdvertisementUsecase) {
    route("/rent") {
        post("/advertisement") {
            val request = call.receive<RentAdvertisement>()

            val rental = rentAdvertisementUsecase(request, call.userId)

            call.respond(HttpStatusCode.Created, rental.toResponse())
        }
    }
}
