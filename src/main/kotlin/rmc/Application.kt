package rmc

import io.ktor.server.application.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.routing.*
import org.h2.engine.User
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import rmc.application.services.CarService
import rmc.application.services.UserService
import rmc.infrastructure.repositories.AddressRepository
import rmc.infrastructure.repositories.CarRepository
import rmc.infrastructure.repositories.UserRepository
import rmc.infrastructure.tables.AddressTable
import rmc.infrastructure.tables.CarTable
import rmc.infrastructure.tables.UserTable
import rmc.presentation.controllers.CarController
import rmc.presentation.controllers.UserController
import rmc.presentation.routes.carRoutes
import rmc.presentation.routes.userRoutes

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
        password = ""
    )

    transaction {
        SchemaUtils.create(
            CarTable,
            AddressTable,
            UserTable,
            )

        CarTable.insert {
            it[licensePlate] = "Car 1"
            it[brand] = "Car 1"
        }
    }

    val carRepository = CarRepository()
    val addressRepository = AddressRepository()
    val userRepository = UserRepository(addressRepository)

    val carService = CarService(carRepository)
    val userService = UserService(userRepository)

    val carController = CarController(carService)
    val userController = UserController(userService)

    routing {
        carRoutes(carController)
        userRoutes(userController)
    }
}