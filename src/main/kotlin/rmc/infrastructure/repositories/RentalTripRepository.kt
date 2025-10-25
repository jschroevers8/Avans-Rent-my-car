package rmc.infrastructure.repositories

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import rmc.domain.entities.RentalTripEntity
import rmc.domain.repositories.RentalTripRepositoryInterface
import rmc.infrastructure.mappers.toRentalTripEntity
import rmc.infrastructure.tables.RentalTripTable

class RentalTripRepository : RentalTripRepositoryInterface {
    override fun save(rentalTrip: RentalTripEntity): RentalTripEntity =
        transaction {
            if (rentalTrip.id == null) {
                val id =
                    RentalTripTable.insert {
                        it[rentalId] = rentalTrip.rentalId
                        it[startMileage] = rentalTrip.startMileage
                        it[endMileage] = rentalTrip.endMileage
                        it[startDate] = rentalTrip.startDate
                        it[endDate] = rentalTrip.endDate
                    } get RentalTripTable.id

                return@transaction rentalTrip.copy(id = id)
            }

            RentalTripTable.update({ RentalTripTable.id eq rentalTrip.id }) {
                it[rentalId] = rentalTrip.rentalId
                it[startMileage] = rentalTrip.startMileage
                it[endMileage] = rentalTrip.endMileage
                it[startDate] = rentalTrip.startDate
                it[endDate] = rentalTrip.endDate
            }

            rentalTrip
        }

    override fun findByRentalId(rentalId: Int): List<RentalTripEntity> =
        transaction {
            RentalTripTable.selectAll().where { RentalTripTable.rentalId eq rentalId }
                .map { it.toRentalTripEntity() }
        }
}
