package rmc.infrastructure.repositories

import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import rmc.domain.entities.CarEntity
import rmc.domain.repositories.CarRepositoryInterface
import rmc.domain.repositories.UserRepositoryInterface
import rmc.infrastructure.mappers.toCarEntity
import rmc.infrastructure.tables.CarTable

class CarRepository(
    private val userRepository: UserRepositoryInterface,
) : CarRepositoryInterface {
    override fun findById(id: Int): CarEntity? =
        transaction {
            CarTable.selectAll().where { CarTable.id eq id }
                .map { it.toCarEntity() }
                .singleOrNull()
        }

    override fun findByLicensePlate(licensePlate: String): CarEntity? =
        transaction {
            CarTable.selectAll().where { CarTable.licensePlate eq licensePlate }
                .map { it.toCarEntity() }
                .singleOrNull()
        }

    override fun save(car: CarEntity): CarEntity =
        transaction {
            if (car.id == null) {
                val id =
                    CarTable.insert {
                        it[fuelType] = car.fuelType
                        it[CarTable.userId] = car.userId
                        it[bodyType] = car.bodyType
                        it[brand] = car.brand
                        it[modelYear] = car.modelYear
                        it[licensePlate] = car.licensePlate
                        it[mileage] = car.mileage
                        it[createdStamp] = car.createdStamp
                    } get CarTable.id

                return@transaction car.copy(id = id)
            }

            CarTable.update({ CarTable.id eq car.id }) {
                it[fuelType] = car.fuelType
                it[CarTable.userId] = userId
                it[bodyType] = car.bodyType
                it[brand] = car.brand
                it[modelYear] = car.modelYear
                it[licensePlate] = car.licensePlate
                it[mileage] = car.mileage
                it[createdStamp] = car.createdStamp
            }

            car
        }

    override fun delete(id: Int): Boolean =
        transaction {
            CarTable.deleteWhere { CarTable.id eq id } > 0
        }

    override fun getAll(): List<CarEntity> =
        transaction {
            CarTable.selectAll().map { it.toCarEntity() }
        }
}
