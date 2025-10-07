package rmc.database.entity

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import rmc.database.tables.UserTable
import rmc.dto.user.UserDTO

class UserEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<UserEntity>(UserTable)

    var userType by UserTable.userType
    var address by AddressEntity referencedOn UserTable.addressId
    var email by UserTable.email
    var password by UserTable.password
    var firstName by UserTable.firstName
    var lastName by UserTable.lastName
    var phone by UserTable.phone
    var userPoints by UserTable.userPoints
}

fun UserEntity.toDTO() = UserDTO(
    id.value,
    userType.name,
    address.toDTO(),
    email,
    firstName,
    lastName,
    phone,
    userPoints
)
