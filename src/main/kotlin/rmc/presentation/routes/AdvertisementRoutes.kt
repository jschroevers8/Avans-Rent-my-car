package rmc.presentation.routes

import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import rmc.presentation.controllers.AdvertisementController
import rmc.presentation.controllers.UserController
import rmc.presentation.dto.advertisement.CreateAdvertisement
import rmc.presentation.dto.user.CreateUser

fun Route.advertisementRoutes(advertisementController: AdvertisementController) {

    route("/advertisement") {

        post("/create") {
            val request = call.receive<CreateAdvertisement>()
            val created = advertisementController.createAdvertisement(request)
            call.respond(HttpStatusCode.Created, created)
        }
    }
}