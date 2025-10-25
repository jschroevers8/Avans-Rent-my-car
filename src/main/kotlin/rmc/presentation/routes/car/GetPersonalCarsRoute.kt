package rmc.presentation.routes.car

import io.ktor.http.HttpStatusCode
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.route
import rmc.application.usecases.car.GetPersonalCarsUsecase
import rmc.infrastructure.plugins.userId
import rmc.presentation.mappers.toResponse

fun Route.getPersonalCarsRoute(getPersonalCarsUsecase: GetPersonalCarsUsecase) {
    route("/personal") {
        get("/cars") {
            val cars = getPersonalCarsUsecase(call.userId)

            call.respond(HttpStatusCode.OK, cars.map { it.toResponse() })
        }
    }
}
