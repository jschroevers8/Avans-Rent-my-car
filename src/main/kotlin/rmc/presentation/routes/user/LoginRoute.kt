package rmc.presentation.routes.user

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.request.receiveParameters
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import kotlinx.serialization.json.Json
import org.jetbrains.exposed.sql.kotlin.datetime.Date
import rmc.application.usecases.user.LoginUsecase
import rmc.presentation.dto.user.LoginRequestDTO
import rmc.presentation.mappers.toResponse
import java.util.Date
import kotlin.text.toIntOrNull

fun Route.userLoginRoute(loginUsecase: LoginUsecase) {
    post("/login") {
        val parameters = call.receive<LoginRequestDTO>()
        val email = parameters.email.toString()
        val password = parameters.password.toString()

        val jwtSecret = environment.config.property("jwt.secret").getString()
        val jwtAudience = environment.config.property("jwt.audience").getString()
        val jwtDomain = environment.config.property("jwt.domain").getString()

        val user = loginUsecase.invoke(email)

        if (user?.password == password) {
            val token = JWT.create()
                .withAudience(jwtAudience)
                .withIssuer(jwtDomain)
                .withClaim("userId", user.id)
                .withExpiresAt(Date(System.currentTimeMillis() + 24*60*60000))
                .sign(Algorithm.HMAC256(jwtSecret))
            call.respondText(
                Json.encodeToString(hashMapOf("token" to token)),
                ContentType.Application.Json
            )
        } else {
            call.response.status(HttpStatusCode.Unauthorized)
            call.respondText(
                Json.encodeToString(hashMapOf("error" to "Wrong login or password")),
                ContentType.Application.Json
            )
        }
    }
}
