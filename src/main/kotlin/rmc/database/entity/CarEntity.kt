package rmc.database.entity

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import rmc.database.tables.CarTable
import rmc.dto.car.CarDTO

class CarEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<CarEntity>(CarTable)

    var user by UserEntity referencedOn CarTable.userId
    var fuelType by CarTable.fuelType
    var bodyType by CarTable.bodyType
    var brand by CarTable.brand
    var model by CarTable.model
    var modelYear by CarTable.modelYear
    var licensePlate by CarTable.licensePlate
    var mileage by CarTable.mileage
    var createdStamp by CarTable.createdStamp
}

fun CarEntity.toDTO() = CarDTO(
    id.value,
    brand,
    model,
    modelYear,
    licensePlate,
    mileage,
    fuelType.name,
    bodyType.name,
    user.toDTO()
)
