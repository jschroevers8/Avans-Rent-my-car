package rmc.infrastructure.tables

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.kotlin.datetime.datetime

object AdvertisementTable : Table() {
    val id = integer("id").autoIncrement()
    val carId = integer("car_id").references(CarTable.id)
    val addressId = integer("address_id").references(AddressTable.id)
    val availableFrom = datetime("available_form")
    val availableUntil = datetime("available_until")
    val price = double("price")
    override val primaryKey = PrimaryKey(id)
}
