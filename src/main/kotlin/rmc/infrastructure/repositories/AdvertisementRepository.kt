package rmc.infrastructure.repositories

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import rmc.domain.entities.AdvertisementEntity
import rmc.domain.repositories.AddressRepositoryInterface
import rmc.domain.repositories.AdvertisementRepositoryInterface
import rmc.infrastructure.mappers.toAdvertisementEntity
import rmc.infrastructure.tables.AdvertisementTable

class AdvertisementRepository(
    private val addressRepository: AddressRepositoryInterface,
) : AdvertisementRepositoryInterface {
    override fun getAll(): List<AdvertisementEntity> =
        transaction {
            AdvertisementTable.selectAll().map { it.toAdvertisementEntity(addressRepository) }
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
                    it[pickUpDate] = advertisement.pickUpDate
                    it[returningDate] = advertisement.returningDate
                    it[price] = advertisement.price
                } get AdvertisementTable.id

            advertisement.copy(id = id)
        }
}
