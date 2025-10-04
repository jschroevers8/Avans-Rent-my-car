package rmc.repository.car

import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import rmc.database.entity.CarEntity
import rmc.database.entity.toCarDTO
import rmc.database.tables.CarTable
import rmc.dto.car.CarDTO


class CarRepository : CarRepositoryInterface {
    override suspend fun getAllCars(): List<CarDTO>  {
        return listOf(CarDTO(1, "BMW"), CarDTO(2, "Tesla"))

//        CarTable.selectAll().map {
//            CarEntity(
//                id = it[CarTable.id],
//            ).toCarDTO()
//        }
    }
}