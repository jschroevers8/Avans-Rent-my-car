package rmc.database.entity

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import rmc.database.tables.CarTable
import rmc.dto.car.CarDTO

class CarEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<CarEntity>(CarTable)

    var brand by CarTable.brand
}

fun CarEntity.toCarDTO() = CarDTO(
    this.id.value,
    this.brand,
)