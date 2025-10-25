package rmc.presentation.routes.car

import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import rmc.application.usecases.car.UpdateCarUsecase
import rmc.infrastructure.plugins.userId
import rmc.presentation.dto.car.UpdateCar
import rmc.presentation.mappers.toResponse

fun Route.updateCarRoute(updateCarUsecase: UpdateCarUsecase) {
    put("/car/update/{id}") {
        val carId =
            call.parameters["id"]?.toIntOrNull()
                ?: return@put call.respond(HttpStatusCode.BadRequest, "Invalid or missing id parameter")

        val carRequest = call.receive<UpdateCar>()
        val requestWithId = carRequest.copy(id = carId)

        val updatedCar = updateCarUsecase(requestWithId, call.userId)
        call.respond(HttpStatusCode.OK, updatedCar.toResponse())
    }
}
