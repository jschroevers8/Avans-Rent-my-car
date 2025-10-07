package rmc.database.tables

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption
import rmc.enum.UserType

object UserTable : IntIdTable("User") {
    val userType = enumerationByName<UserType>("UserType", 100)
    val addressId = reference("addressId", AddressTable, onDelete = ReferenceOption.CASCADE)
//    val email = varchar("email", 100).uniqueIndex()
// just for testing
    val email = varchar("email", 100)
    val password = varchar("password", 255)
    val firstName = varchar("firstName", 100)
    val lastName = varchar("lastName", 100)
    val phone = varchar("phone", 50)
    val userPoints = integer("userPoints").default(0)
}
