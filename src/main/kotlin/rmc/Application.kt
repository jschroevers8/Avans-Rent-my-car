package rmc

import io.ktor.server.application.*
import io.ktor.server.netty.EngineMain
import rmc.database.DatabaseFactory
import rmc.plugins.configureRouting
import rmc.plugins.configureSerialization

fun main(args: Array<String>) {
    EngineMain.main(args)
}

fun Application.module() {
    DatabaseFactory.init()
    configureSerialization()
    configureRouting()
}
