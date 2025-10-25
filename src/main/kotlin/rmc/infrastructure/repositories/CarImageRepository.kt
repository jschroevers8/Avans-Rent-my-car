package rmc.infrastructure.repositories

import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import rmc.domain.entities.CarEntity
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

    override fun deleteAllByCar(car: CarEntity): Boolean =
        transaction {
            CarImageTable.deleteWhere { CarImageTable.carId eq car.id!! } > 0
        }

    override fun save(carImage: CarImageEntity): CarImageEntity =
        transaction {
            if (carImage.id == null) {
                val id =
                    CarImageTable.insert {
                        it[carId] = carImage.carId
                        it[image] = carImage.image
                        it[weight] = carImage.weight
                    } get CarImageTable.id

                return@transaction carImage.copy(id = id)
            }

            CarImageTable.update({ CarImageTable.id eq carImage.id }) {
                it[carId] = carImage.carId
                it[image] = carImage.image
                it[weight] = carImage.weight
            }

            carImage
        }
}
