package rmc.presentation.routes.car

import io.ktor.http.HttpStatusCode
import io.ktor.server.auth.authenticate
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.route
import rmc.application.usecases.car.GetCarUsecase
import rmc.presentation.mappers.toResponse
import kotlin.text.toIntOrNull

fun Route.getCarRoute(getCarUsecase: GetCarUsecase) {
    authenticate("myAuth") {
        route("/car") {
            get("/show/{id}") {
                val carId =
                    call.parameters["id"]?.toIntOrNull()
                        ?: return@get call.respond(HttpStatusCode.BadRequest, "Invalid or missing id parameter")

                val car = getCarUsecase(carId)

                call.respond(HttpStatusCode.OK, car.toResponse())
            }
        }
    }
}
