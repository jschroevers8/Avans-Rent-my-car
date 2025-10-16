package rmc.presentation.routes

import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import rmc.presentation.controllers.AdvertisementController
import rmc.presentation.dto.advertisement.CreateAdvertisement

fun Route.advertisementRoutes(advertisementController: AdvertisementController) {
    route("/advertisement") {
        post("/create") {
            val request = call.receive<CreateAdvertisement>()
            val created = advertisementController.createAdvertisement(request)
            call.respond(HttpStatusCode.Created, created)
        }

        get("/all") {
            val cars = advertisementController.getAllAdvertisements()

            call.respond(HttpStatusCode.OK, cars)
        }

        get("/{id}") {
            val advertisementId = call.parameters["id"]?.toIntOrNull()

            if (advertisementId == null) {
                call.respond(HttpStatusCode.BadRequest, "Invalid or missing id parameter")
                return@get
            }

            val advertisement = advertisementController.findAllAdvertisementById(advertisementId)
            if (advertisement == null) {
                call.respond(HttpStatusCode.NotFound, "Advertisement with id $advertisementId not found")
                return@get
            }

            call.respond(HttpStatusCode.OK, advertisement)
        }
    }
}
