package rmc.infrastructure.repositories

import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import rmc.domain.entities.CarEntity
import rmc.domain.repositories.CarRepositoryInterface
import rmc.infrastructure.mappers.toCarEntity
import rmc.infrastructure.tables.CarTable


class CarRepository : CarRepositoryInterface {
    override suspend fun findById(id: Int): CarEntity? {
        TODO("Not yet implemented")
    }

    override suspend fun findByLicensePlate(licensePlate: String): CarEntity? = transaction {
        CarTable.selectAll().where { CarTable.licensePlate eq licensePlate }
            .map { it.toCarEntity() }
            .singleOrNull()
    }

    override suspend fun save(car: CarEntity): CarEntity = transaction {

        if (car.id == null) {
            val id = CarTable.insert {
                it[licensePlate] = car.licensePlate
                it[brand] = car.brand
            } get CarTable.id

            return@transaction car.copy(id = id)
        }

        CarTable.update({ CarTable.id eq car.id }) {
            it[licensePlate] = car.licensePlate
            it[brand] = car.brand
        }

        car
    }

    override suspend fun delete(id: Int): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun getAll(): List<CarEntity> = transaction {
        CarTable.selectAll().map { it.toCarEntity() }
    }
}