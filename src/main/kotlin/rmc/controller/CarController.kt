package rmc.controller

import io.ktor.server.response.respond
import io.ktor.server.routing.*
import rmc.repository.car.CarRepositoryInterface

class CarController(private val carRepository: CarRepositoryInterface) {

    fun registerRoutes(route: Route) {
        route.apply {
            get("/cars") {
                val cars = carRepository.getAllCars()
                call.respond(cars)
            }
        }
    }
}
