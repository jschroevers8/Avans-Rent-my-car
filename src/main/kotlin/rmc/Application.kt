package rmc

import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import rmc.infrastructure.tables.AddressTable
import rmc.infrastructure.tables.AdvertisementTable
import rmc.infrastructure.tables.CarImageTable
import rmc.infrastructure.tables.CarTable
import rmc.infrastructure.tables.RentalTable
import rmc.infrastructure.tables.RentalTripTable
import rmc.infrastructure.tables.UserTable

fun main(args: Array<String>) {
    EngineMain.main(args)
}

fun Application.module() {
    install(ContentNegotiation) {
        json()
    }

    Database.connect(
        url = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;",
        driver = "org.h2.Driver",
        user = "root",
        password = "",
    )

    transaction {
        SchemaUtils.createMissingTablesAndColumns(
            CarTable,
            AddressTable,
            UserTable,
            AdvertisementTable,
            CarImageTable,
            RentalTable,
            RentalTripTable,
        )
    }

    configureRouting()
}
