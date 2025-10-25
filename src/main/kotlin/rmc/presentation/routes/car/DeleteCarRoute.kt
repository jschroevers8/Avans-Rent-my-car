package rmc.presentation.routes.car

import io.ktor.http.HttpStatusCode
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.route
import rmc.application.usecases.car.DeleteCarUsecase
import rmc.infrastructure.plugins.userId
import kotlin.text.toIntOrNull

fun Route.deleteCarRoute(deleteCarUsecase: DeleteCarUsecase) {
    route("/car") {
        delete("/delete/{id}") {
            val carId =
                call.parameters["id"]?.toIntOrNull()
                    ?: return@delete call.respond(HttpStatusCode.BadRequest, "Invalid or missing id parameter")

            deleteCarUsecase(carId, call.userId)

            call.respond(HttpStatusCode.NoContent)
        }
    }
}
