package rmc.presentation.routes.advertisement

import io.ktor.http.HttpStatusCode
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.route
import rmc.application.usecases.advertisement.DeleteAdvertisementUsecase
import rmc.infrastructure.plugins.userId
import kotlin.text.toIntOrNull

fun Route.deleteAdvertisementRoute(deleteAdvertisementUsecase: DeleteAdvertisementUsecase) {
    route("/advertisement") {
        delete("/delete/{id}") {
            val advertisementId =
                call.parameters["id"]?.toIntOrNull()
                    ?: return@delete call.respond(HttpStatusCode.BadRequest, "Invalid or missing id parameter")

            deleteAdvertisementUsecase(advertisementId, call.userId)

            call.respond(HttpStatusCode.NoContent)
        }
    }
}
