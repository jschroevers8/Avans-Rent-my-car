package rmc.infrastructure.plugins

import io.ktor.server.application.ApplicationCall
import io.ktor.server.auth.UserIdPrincipal
import io.ktor.server.auth.principal
import rmc.application.exceptions.InvalidUserException

val ApplicationCall.userId: Int
    get() =
        principal<UserIdPrincipal>()?.name?.toIntOrNull()
            ?: throw InvalidUserException("Invalid or missing userId in token")
