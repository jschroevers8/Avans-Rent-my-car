package rmc.database.tables

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption
import rmc.enum.BodyType
import rmc.enum.FuelType
import org.jetbrains.exposed.sql.kotlin.datetime.date


object CarTable : IntIdTable() {
    val userId = reference("userId", UserTable, onDelete = ReferenceOption.CASCADE)
    val fuelType = enumerationByName<FuelType>("fuelType", 50)
    val bodyType = enumerationByName<BodyType>("bodyType", 50)
    val brand = varchar("brand", 100)
    val model = varchar("model", 100)
    val modelYear = integer("modelYear")
//    val licensePlate = varchar("licensePlate", 50).uniqueIndex()
// just for testing
    val licensePlate = varchar("licensePlate", 50)
    val mileage = integer("mileage").default(0)
    val createdStamp = date("createdStamp")

    init {
        index(true, licensePlate)
    }
}