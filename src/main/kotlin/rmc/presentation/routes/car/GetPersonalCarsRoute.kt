package rmc.presentation.routes.car

import io.ktor.http.HttpStatusCode
import io.ktor.server.auth.UserIdPrincipal
import io.ktor.server.auth.authenticate
import io.ktor.server.auth.principal
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.route
import rmc.application.usecases.car.GetPersonalCarsUsecase
import rmc.presentation.mappers.toResponse
import kotlin.text.toIntOrNull

fun Route.getPersonalCarsRoute(getPersonalCarsUsecase: GetPersonalCarsUsecase) {
    authenticate("myAuth") {
        route("/personal") {
            get("/cars") {
                val userId = call.principal<UserIdPrincipal>()?.name?.toIntOrNull()
                if (userId == null) {
                    call.respond(HttpStatusCode.BadRequest, "Invalid userId in token")
                    return@get
                }

                val cars = getPersonalCarsUsecase(userId)
                call.respond(HttpStatusCode.OK, cars.map { it.toResponse() })
            }
        }
    }
}
