package rmc.infrastructure.mappers

import org.jetbrains.exposed.sql.ResultRow
import rmc.domain.entities.RentalEntity
import rmc.infrastructure.tables.RentalTable

fun ResultRow.toRentalEntity() =
    RentalEntity(
        id = this[RentalTable.id],
        userId = this[RentalTable.userId],
        advertisementId = this[RentalTable.advertisementId],
        rentalStatus = this[RentalTable.rentalStatus],
        returningDate = this[RentalTable.returningDate],
        pickUpDate = this[RentalTable.pickUpDate],
    )
