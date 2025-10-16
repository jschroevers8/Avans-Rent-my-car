package rmc.infrastructure.mappers

import org.jetbrains.exposed.sql.ResultRow
import rmc.domain.entities.UserEntity
import rmc.domain.repositories.AddressRepositoryInterface
import rmc.infrastructure.tables.UserTable

fun ResultRow.toUserEntity(addressRepository: AddressRepositoryInterface): UserEntity {
    val addressId = this[UserTable.addressId]
    val address = addressId.let { addressRepository.findById(it)!! }

    return UserEntity(
        id = this[UserTable.id],
        userType = this[UserTable.userType],
        address = address,
        email = this[UserTable.email],
        password = this[UserTable.password],
        firstName = this[UserTable.firstName],
        lastName = this[UserTable.lastName],
        phone = this[UserTable.phone],
        userPoints = this[UserTable.userPoints],
    )
}
