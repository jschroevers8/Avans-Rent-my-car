package rmc.infrastructure.tables

import org.jetbrains.exposed.sql.Table

object AddressTable : Table() {
    val id = integer("id").autoIncrement()
    val city = varchar("city", 50)
    val street = varchar("street", 100)
    val houseNumber = integer("houseNumber")
    val subHouseNumber = varchar("subHouseNumber", 100).nullable()
    val postalCode = varchar("postalCode", 20)
    override val primaryKey = PrimaryKey(id)
}
