package rmc.presentation.routes.car

import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import rmc.application.usecases.car.UpdateCarUsecase
import rmc.presentation.dto.car.UpdateCar
import rmc.presentation.mappers.toResponse

fun Route.updateCarRoute(updateCarUsecase: UpdateCarUsecase) {
    put("/car/update/{id}") {
        val id = call.parameters["id"]?.toIntOrNull()
        if (id == null) {
            call.respond(HttpStatusCode.BadRequest, "Invalid car id")
            return@put
        }

        val carRequest = call.receive<UpdateCar>()
        val requestWithId = carRequest.copy(id = id)

        val updatedCar = updateCarUsecase(requestWithId)
        call.respond(HttpStatusCode.OK, updatedCar.toResponse())
    }
}
