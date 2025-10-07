package rmc.database.tables

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.kotlin.datetime.date

object AdvertisementTable : IntIdTable("Advertisement") {
    val carId = reference("carId", CarTable, onDelete = ReferenceOption.CASCADE)
    val addressId = reference("addressId", AddressTable, onDelete = ReferenceOption.SET_NULL).nullable()
    val pickupDate = date("pickupDate")
    val returningDate = date("returningDate")
    val price = integer("price")
}