package rmc.infrastructure.repositories

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import rmc.domain.entities.AdvertisementEntity
import rmc.domain.entities.RentalEntity
import rmc.domain.repositories.RentalRepositoryInterface
import rmc.infrastructure.mappers.toRentalEntity
import rmc.infrastructure.tables.RentalTable

class RentalRepository : RentalRepositoryInterface {
    override fun findById(id: Int): RentalEntity? =
        transaction {
            RentalTable.selectAll().where { RentalTable.id eq id }
                .map { it.toRentalEntity() }
                .singleOrNull()
        }

    override fun findAllByAdvertisement(advertisement: AdvertisementEntity): List<RentalEntity> =
        transaction {
            RentalTable.selectAll().where { RentalTable.advertisementId eq advertisement.id!! }
                .map { it.toRentalEntity() }
        }

    override fun save(rental: RentalEntity): RentalEntity =
        transaction {
            val id =
                RentalTable.insert {
                    it[userId] = rental.userId
                    it[advertisementId] = rental.advertisementId
                    it[rentalStatus] = rental.rentalStatus
                    it[pickUpDate] = rental.pickUpDate
                    it[returningDate] = rental.returningDate
                } get RentalTable.id
            rental.copy(id = id)
        }

    override fun update(rental: RentalEntity): RentalEntity =
        transaction {
            RentalTable.update({ RentalTable.id eq rental.id!! }) {
                it[userId] = rental.userId
                it[advertisementId] = rental.advertisementId
                it[rentalStatus] = rental.rentalStatus
                it[pickUpDate] = rental.pickUpDate
                it[returningDate] = rental.returningDate
            }

            rental
        }

    override fun getAll(): List<RentalEntity> =
        transaction {
            RentalTable.selectAll().map { it.toRentalEntity() }
        }

    override fun delete(id: Int): Boolean =
        transaction {
            RentalTable.deleteWhere { RentalTable.id eq id } > 0
        }
}
