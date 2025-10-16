package rmc.infrastructure.mappers

import org.jetbrains.exposed.sql.ResultRow
import rmc.domain.entities.AddressEntity
import rmc.infrastructure.tables.AddressTable

fun ResultRow.toAddressEntity() =
    AddressEntity(
        id = this[AddressTable.id],
        city = this[AddressTable.city],
        street = this[AddressTable.street],
        houseNumber = this[AddressTable.houseNumber],
        subHouseNumber = this[AddressTable.subHouseNumber],
        postalCode = this[AddressTable.postalCode],
    )
