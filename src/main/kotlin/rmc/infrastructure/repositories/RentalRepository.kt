package rmc.infrastructure.repositories

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import rmc.domain.entities.RentalEntity
import rmc.domain.repositories.RentalRepositoryInterface
import rmc.infrastructure.mappers.toRentalEntity
import rmc.infrastructure.tables.CarTable.userId
import rmc.infrastructure.tables.RentalTable

class RentalRepository : RentalRepositoryInterface {
    override fun findById(id: Int): RentalEntity? =
        transaction {
            RentalTable.selectAll().where { RentalTable.id eq id }
                .map { it.toRentalEntity() }
                .singleOrNull()
        }

    override fun findAllByAdvertisementId(advertisementId: Int): List<RentalEntity> =
        transaction {
            RentalTable.selectAll().where { RentalTable.advertisementId eq advertisementId }
                .map { it.toRentalEntity() }
        }

    override fun save(rental: RentalEntity): RentalEntity =
        transaction {
            if (rental.id == null) {
                val id =
                    RentalTable.insert {
                        it[userId] = rental.userId
                        it[advertisementId] = rental.advertisementId
                        it[rentalStatus] = rental.rentalStatus
                        it[pickUpDate] = rental.pickUpDate
                        it[returningDate] = rental.returningDate
                    } get RentalTable.id

                return@transaction rental.copy(id = id)
            }

            RentalTable.update({ RentalTable.id eq rental.id }) {
                it[userId] = rental.userId
                it[advertisementId] = rental.advertisementId
                it[rentalStatus] = rental.rentalStatus
                it[pickUpDate] = rental.pickUpDate
                it[returningDate] = rental.returningDate
            }

            rental
        }
}
