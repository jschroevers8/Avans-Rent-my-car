package rmc.infrastructure.mappers

import org.jetbrains.exposed.sql.ResultRow
import rmc.domain.entities.CarEntity
import rmc.infrastructure.tables.CarTable

fun ResultRow.toCarEntity() =
    CarEntity(
        id = this[CarTable.id],
        fuelType = this[CarTable.fuelType],
        userId = this[CarTable.userId],
        bodyType = this[CarTable.bodyType],
        brand = this[CarTable.brand],
        modelYear = this[CarTable.modelYear],
        licensePlate = this[CarTable.licensePlate],
        mileage = this[CarTable.mileage],
        createdStamp = this[CarTable.createdStamp],
    )
