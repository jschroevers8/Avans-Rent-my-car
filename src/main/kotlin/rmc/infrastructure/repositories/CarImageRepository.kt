package rmc.infrastructure.repositories

import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import rmc.domain.entities.CarImageEntity
import rmc.domain.repositories.CarImageRepositoryInterface
import rmc.infrastructure.mappers.toCarImageEntity
import rmc.infrastructure.tables.CarImageTable

class CarImageRepository() : CarImageRepositoryInterface {
    override fun findByCarId(carId: Int): List<CarImageEntity> =
        transaction {
            CarImageTable.selectAll().where { CarImageTable.carId eq carId }
                .map { it.toCarImageEntity() }
        }

    override fun save(carImage: CarImageEntity): CarImageEntity =
        transaction {
            val id =
                CarImageTable.insert {
                    it[carId] = carImage.carId
                    it[image] = carImage.image
                    it[weight] = carImage.weight
                } get CarImageTable.id

            carImage.copy(id = id)
        }
}
