package rmc.repository.car

import org.jetbrains.exposed.sql.transactions.transaction
import rmc.database.DatabaseFactory
import rmc.database.entity.CarEntity
import rmc.database.entity.toDTO
import rmc.dto.car.CarDTO


class CarRepository : CarRepositoryInterface {
    override suspend fun getAllCars(): List<CarDTO> =
        DatabaseFactory.dbQuery {
            CarEntity.all().map { it.toDTO() }
        }
}