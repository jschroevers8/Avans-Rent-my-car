package rmc.presentation.routes.car

import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import rmc.application.usecases.car.CreateCarUsecase
import rmc.application.usecases.car.GetCarUsecase
import rmc.presentation.dto.car.CreateCar
import rmc.presentation.mappers.toResponse
import kotlin.text.toIntOrNull

fun Route.getCarRoute(getCarUsecase: GetCarUsecase) {
    route("/car") {
        get("/show/{id}") {
            val carId = call.parameters["id"]?.toIntOrNull()

            if (carId == null) {
                call.respond(HttpStatusCode.BadRequest, "Invalid or missing id parameter")
                return@get
            }

            val car = getCarUsecase.invoke(carId)
            if (car == null) {
                call.respond(HttpStatusCode.NotFound, "Car with id $carId not found")
                return@get
            }

            call.respond(HttpStatusCode.OK, car.toResponse())
        }
    }
}
