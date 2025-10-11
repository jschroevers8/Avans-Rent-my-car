package rmc

import io.ktor.server.application.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import rmc.application.services.AdvertisementService
import rmc.application.services.CarService
import rmc.application.services.UserService
import rmc.infrastructure.repositories.AddressRepository
import rmc.infrastructure.repositories.AdvertisementRepository
import rmc.infrastructure.repositories.CarRepository
import rmc.infrastructure.repositories.UserRepository
import rmc.infrastructure.tables.AddressTable
import rmc.infrastructure.tables.AdvertisementTable
import rmc.infrastructure.tables.CarTable
import rmc.infrastructure.tables.UserTable
import rmc.presentation.controllers.AdvertisementController
import rmc.presentation.controllers.CarController
import rmc.presentation.controllers.UserController
import rmc.presentation.routes.advertisementRoutes
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
            AdvertisementTable,
            )
    }

    val addressRepository = AddressRepository()
    val userRepository = UserRepository(addressRepository)
    val carRepository = CarRepository(userRepository)
    val advertisementRepository = AdvertisementRepository(addressRepository)

    val carService = CarService(carRepository, userRepository)
    val userService = UserService(userRepository)
    val advertisementService = AdvertisementService(carRepository, advertisementRepository)

    val carController = CarController(carService)
    val userController = UserController(userService)
    val advertisementController = AdvertisementController(advertisementService)

    routing {
        carRoutes(carController)
        userRoutes(userController)
        advertisementRoutes(advertisementController)
    }
}