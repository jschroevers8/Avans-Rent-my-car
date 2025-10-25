package rmc.infrastructure.tables

import org.jetbrains.exposed.sql.Table
import rmc.domain.entities.UserType

object UserTable : Table("user") {
    val id = integer("id").autoIncrement()
    val userType = enumerationByName("user_type", 20, UserType::class)
    val addressId = integer("address_id").references(AddressTable.id)
    val email = varchar("email", 100).uniqueIndex()
    val password = varchar("password", 100)
    val firstName = varchar("firstName", 50)
    val lastName = varchar("lastName", 50)
    val phone = varchar("phone", 50)
    val userPoints = integer("userPoints")
    override val primaryKey = PrimaryKey(id)
}
