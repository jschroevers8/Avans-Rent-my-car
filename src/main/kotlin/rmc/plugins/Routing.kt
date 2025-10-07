package rmc.plugins

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import rmc.controller.CarController
import rmc.repository.car.CarRepository

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Hello World!")
        }

        val carController = CarController(CarRepository())
        carController.registerRoutes(this)
    }
}
