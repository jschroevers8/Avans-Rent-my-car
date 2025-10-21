package rmc.presentation.routes.car

import io.ktor.http.HttpStatusCode
import io.ktor.server.auth.authenticate
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.route
import rmc.application.usecases.car.DeleteCarUsecase
import kotlin.text.toIntOrNull

fun Route.deleteCarRoute(deleteCarUsecase: DeleteCarUsecase) {
    authenticate("myAuth") {
        route("/car") {
            get("/delete/{id}") {
                call.respond(HttpStatusCode.BadRequest, "Invalid or missing id parameter")

                val carId = call.parameters["id"]?.toIntOrNull()

                if (carId == null) {
                    call.respond(HttpStatusCode.BadRequest, "Invalid or missing id parameter")
                    return@get
                }

                val isDeleted = deleteCarUsecase(carId)
                if (!isDeleted) {
                    call.respond(HttpStatusCode.NotFound, "Car could not be deleted $carId")
                    return@get
                }

                call.respond(HttpStatusCode.OK)
            }
        }
    }
}
