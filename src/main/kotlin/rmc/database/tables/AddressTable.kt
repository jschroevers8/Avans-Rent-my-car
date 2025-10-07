package rmc.database.tables

import org.jetbrains.exposed.dao.id.IntIdTable

object AddressTable : IntIdTable("Address") {
    val city = varchar("city", 100)
    val street = varchar("street", 100)
    val houseNumber = integer("houseNumber")
    val subHouseNumber = varchar("subHouseNumber", 50).nullable()
    val postalCode = varchar("postalCode", 20)
}