package rmc.controller

import io.ktor.server.response.respondText
import io.ktor.server.routing.*
import rmc.repository.car.CarRepositoryInterface

class CarController(private val carRepository: CarRepositoryInterface) {

    fun registerRoutes(route: Route) {
        route.apply {
            get("/cars") {
                val cars = carRepository.getAllCars()

                val allCarsText = cars.joinToString(separator = "\n") { car ->
                    car.brand
                }

                call.respondText(allCarsText)
            }
        }
    }
}
