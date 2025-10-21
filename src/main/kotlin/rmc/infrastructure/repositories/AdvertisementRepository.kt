package rmc.infrastructure.repositories

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import rmc.domain.entities.AdvertisementEntity
import rmc.domain.entities.RentalStatus
import rmc.domain.repositories.AddressRepositoryInterface
import rmc.domain.repositories.AdvertisementRepositoryInterface
import rmc.infrastructure.mappers.toAdvertisementEntity
import rmc.infrastructure.tables.AdvertisementTable
import rmc.infrastructure.tables.RentalTable

class AdvertisementRepository(
    private val addressRepository: AddressRepositoryInterface,
) : AdvertisementRepositoryInterface {
    override fun getAllAvailable(): List<AdvertisementEntity> =
        transaction {
            val activeRentalAds =
                RentalTable
                    .select(RentalTable.advertisementId)
                    .where { RentalTable.rentalStatus eq RentalStatus.ACTIVE }

            AdvertisementTable
                .selectAll().where { AdvertisementTable.id inSubQuery activeRentalAds }
                .map { it.toAdvertisementEntity(addressRepository) }
        }

    override fun findOneByCarId(carId: Int): AdvertisementEntity? =
        transaction {
            AdvertisementTable.selectAll().where { AdvertisementTable.carId eq carId }
                .map { it.toAdvertisementEntity(addressRepository) }.singleOrNull()
        }

    override fun findById(id: Int): AdvertisementEntity? =
        transaction {
            AdvertisementTable.selectAll().where { AdvertisementTable.id eq id }
                .map { it.toAdvertisementEntity(addressRepository) }.singleOrNull()
        }

    override fun save(advertisement: AdvertisementEntity): AdvertisementEntity =
        transaction {
            val addressId = advertisement.address.let { addressRepository.save(it).id!! }

            val id =
                AdvertisementTable.insert {
                    it[carId] = advertisement.carId
                    it[AdvertisementTable.addressId] = addressId
                    it[availableFrom] = advertisement.availableFrom
                    it[availableUntil] = advertisement.availableUntil
                    it[price] = advertisement.price
                } get AdvertisementTable.id

            advertisement.copy(id = id)
        }

    override fun delete(id: Int): Boolean =
        transaction {
            AdvertisementTable.deleteWhere { AdvertisementTable.id eq id } > 0
        }
}
