package rmc.domain.repositories

import rmc.domain.entities.AddressEntity

interface AddressRepositoryInterface {
    fun findById(id: Int): AddressEntity?
    fun save(address: AddressEntity): AddressEntity
}