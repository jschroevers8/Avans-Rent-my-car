package rmc.infrastructure.mappers

import org.jetbrains.exposed.sql.ResultRow
import rmc.domain.entities.AdvertisementEntity
import rmc.domain.repositories.AddressRepositoryInterface
import rmc.infrastructure.tables.AdvertisementTable

fun ResultRow.toAdvertisementEntity(addressRepository: AddressRepositoryInterface): AdvertisementEntity {
    val addressId = this[AdvertisementTable.addressId]
    val address = addressId.let { addressRepository.findById(it)!! }

    return AdvertisementEntity(
        id = this[AdvertisementTable.id],
        carId = this[AdvertisementTable.carId],
        address = address,
        availableFrom = this[AdvertisementTable.availableFrom],
        availableUntil = this[AdvertisementTable.availableUntil],
        price = this[AdvertisementTable.price],
    )
}
