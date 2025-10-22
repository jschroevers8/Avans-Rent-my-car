package rmc.presentation.routes.car

import io.ktor.http.HttpStatusCode
import io.ktor.server.auth.authenticate
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.route
import rmc.application.usecases.car.DeleteCarUsecase
import kotlin.text.toIntOrNull

fun Route.deleteCarRoute(deleteCarUsecase: DeleteCarUsecase) {
    authenticate("myAuth") {
        route("/car") {
            delete("/delete/{id}") {
                val carId = call.parameters["id"]?.toIntOrNull()

                if (carId == null) {
                    call.respond(HttpStatusCode.BadRequest, "Invalid or missing id parameter")

                    return@delete
                }

                deleteCarUsecase(carId)

                call.respond(HttpStatusCode.NoContent)
            }
        }
    }
}
