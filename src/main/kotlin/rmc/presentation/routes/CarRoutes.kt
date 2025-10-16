package rmc.presentation.routes

import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import rmc.presentation.controllers.CarController
import rmc.presentation.dto.car.CreateCar

fun Route.carRoutes(carController: CarController) {
    route("/car") {
        get("/all") {
            val cars = carController.getAllCars()

            call.respond(HttpStatusCode.OK, cars)
        }

        post("/create") {
            val request = call.receive<CreateCar>()

            val car = carController.createCar(request)

            call.respond(HttpStatusCode.Created, car)
        }

        get("/{id}") {
            val carId = call.parameters["id"]?.toIntOrNull()

            if (carId == null) {
                call.respond(HttpStatusCode.BadRequest, "Invalid or missing id parameter")
                return@get
            }

            val car = carController.findCarById(carId)
            if (car == null) {
                call.respond(HttpStatusCode.NotFound, "Car with id $carId not found")
                return@get
            }

            call.respond(HttpStatusCode.OK, car)
        }
    }

/*    route("/users/{userId}/cars") {

        post {
            val userId = call.parameters["userId"]?.toIntOrNull()
                ?: return@post call.respond(HttpStatusCode.BadRequest, "Invalid userId")

            val request = call.receive<CarRequest>()
            val car = carController.createCar(userId, request)
            call.respond(HttpStatusCode.Created, car)
        }

        get {
            val cars = carController.getAllCars()
            call.respond(HttpStatusCode.OK, cars)
        }
    }

    route("/cars/{carId}") {
        get {
            val carId = call.parameters["carId"]?.toIntOrNull()
                ?: return@get call.respond(HttpStatusCode.BadRequest, "Invalid carId")

            val car = carController.getCar(carId)
                ?: return@get call.respond(HttpStatusCode.NotFound, "Car not found")

            call.respond(HttpStatusCode.OK, car)
        }
    }*/
}
