package rmc.infrastructure.mappers

import org.jetbrains.exposed.sql.ResultRow
import rmc.domain.entities.CarImageEntity
import rmc.infrastructure.tables.CarImageTable

fun ResultRow.toCarImageEntity() =
    CarImageEntity(
        id = this[CarImageTable.id],
        carId = this[CarImageTable.carId],
        image = this[CarImageTable.image],
        weight = this[CarImageTable.weight],
    )
