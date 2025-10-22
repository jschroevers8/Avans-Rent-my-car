package rmc

import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import rmc.infrastructure.plugins.configureRouting
import rmc.infrastructure.plugins.configureSecurity
import rmc.infrastructure.plugins.configureStatusPages
import rmc.infrastructure.plugins.initDatabase

fun main(args: Array<String>) {
    EngineMain.main(args)
}

fun Application.module() {
    install(ContentNegotiation) {
        json()
    }

    initDatabase()
    configureSecurity()
    configureRouting()
    configureStatusPages()
}
