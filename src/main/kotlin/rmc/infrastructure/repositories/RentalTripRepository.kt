package rmc.infrastructure.repositories

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import rmc.domain.entities.RentalTripEntity
import rmc.domain.repositories.RentalTripRepositoryInterface
import rmc.infrastructure.mappers.toRentalTripEntity
import rmc.infrastructure.tables.RentalTripTable

class RentalTripRepository : RentalTripRepositoryInterface {
    override fun findById(id: Int): RentalTripEntity? =
        transaction {
            RentalTripTable.selectAll().where { RentalTripTable.id eq id }
                .map { it.toRentalTripEntity() }
                .singleOrNull()
        }

    override fun save(rentalTrip: RentalTripEntity): RentalTripEntity =
        transaction {
            val id =
                RentalTripTable.insert {
                    it[rentalId] = rentalTrip.rentalId
                    it[startMileage] = rentalTrip.startMileage
                    it[endMileage] = rentalTrip.endMileage
                    it[startDate] = rentalTrip.startDate
                    it[endDate] = rentalTrip.endDate
                } get RentalTripTable.id
            rentalTrip.copy(id = id)
        }

    override fun getAll(): List<RentalTripEntity> =
        transaction {
            RentalTripTable.selectAll().map { it.toRentalTripEntity() }
        }

    override fun delete(id: Int): Boolean =
        transaction {
            RentalTripTable.deleteWhere { RentalTripTable.id eq id } > 0
        }
}
