package rmc.database.entity

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import rmc.database.tables.AddressTable
import rmc.dto.address.AddressDTO

class AddressEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<AddressEntity>(AddressTable)

    var city by AddressTable.city
    var street by AddressTable.street
    var houseNumber by AddressTable.houseNumber
    var subHouseNumber by AddressTable.subHouseNumber
    var postalCode by AddressTable.postalCode
}

fun AddressEntity.toDTO() = AddressDTO(
    id.value,
    city,
    street,
    houseNumber,
    subHouseNumber,
    postalCode
)