package rmc.presentation.routes.user

import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respondText
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import kotlinx.serialization.json.Json
import rmc.application.auth.Authenticator
import rmc.application.usecases.user.LoginUsecase
import rmc.presentation.dto.user.LoginRequestDTO

fun Route.userLoginRoute(
    loginUsecase: LoginUsecase,
    authenticator: Authenticator,
) {
    post("/login") {
        val request = call.receive<LoginRequestDTO>()
        val user = loginUsecase.invoke(request.email, request.password)

        if (user != null) {
            val token = authenticator.generateToken(user)
            call.respondText(
                Json.encodeToString(hashMapOf("token" to token)),
                ContentType.Application.Json,
            )
        } else {
            call.response.status(HttpStatusCode.Unauthorized)
            call.respondText(
                Json.encodeToString(hashMapOf("error" to "Wrong login or password")),
                ContentType.Application.Json,
            )
        }
    }
}
