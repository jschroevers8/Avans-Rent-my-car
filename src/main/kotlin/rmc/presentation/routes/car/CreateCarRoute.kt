package rmc.presentation.routes.car

import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import rmc.application.usecases.car.CreateCarUsecase
import rmc.infrastructure.plugins.userId
import rmc.presentation.dto.car.CreateCar
import rmc.presentation.mappers.toResponse

fun Route.createCarRoute(createCarUsecase: CreateCarUsecase) {
    route("/car") {
        post("/create") {
            val request = call.receive<CreateCar>()

            val car = createCarUsecase(request, call.userId)

            call.respond(HttpStatusCode.Created, car.toResponse())
        }
    }
}
