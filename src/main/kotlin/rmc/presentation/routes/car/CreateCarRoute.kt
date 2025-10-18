package rmc.presentation.routes.car

import io.ktor.http.HttpStatusCode
import io.ktor.server.auth.authenticate
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import rmc.application.usecases.car.CreateCarUsecase
import rmc.presentation.dto.car.CreateCar
import rmc.presentation.mappers.toResponse

fun Route.createCarRoute(createCarUsecase: CreateCarUsecase) {
    authenticate("myAuth") {
        route("/car") {
            post("/create") {
                val request = call.receive<CreateCar>()

                val car = createCarUsecase.invoke(request)

                call.respond(HttpStatusCode.Created, car.toResponse())
            }
        }
    }
}
