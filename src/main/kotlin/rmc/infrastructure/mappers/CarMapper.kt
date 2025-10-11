package rmc.infrastructure.mappers

import org.jetbrains.exposed.sql.ResultRow
import rmc.domain.entities.CarEntity
import rmc.infrastructure.tables.CarTable

fun ResultRow.toCarEntity() = CarEntity(
    id = this[CarTable.id],
    licensePlate = this[CarTable.licensePlate],
    brand = this[CarTable.brand],
)