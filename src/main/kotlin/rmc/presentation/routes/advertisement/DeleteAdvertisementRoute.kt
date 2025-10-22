package rmc.presentation.routes.advertisement

import io.ktor.http.HttpStatusCode
import io.ktor.server.auth.authenticate
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.route
import rmc.application.usecases.advertisement.DeleteAdvertisementUsecase
import kotlin.text.toIntOrNull

fun Route.deleteAdvertisementRoute(deleteAdvertisementUsecase: DeleteAdvertisementUsecase) {
    authenticate("myAuth") {
        route("/advertisement") {
            delete("/delete/{id}") {
                val advertisementId = call.parameters["id"]?.toIntOrNull()

                if (advertisementId == null) {
                    call.respond(HttpStatusCode.BadRequest, "Invalid or missing id parameter")

                    return@delete
                }

                deleteAdvertisementUsecase(advertisementId)

                call.respond(HttpStatusCode.NoContent)
            }
        }
    }
}
