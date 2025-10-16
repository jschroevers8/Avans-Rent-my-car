package rmc.infrastructure.repositories

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import rmc.domain.entities.AddressEntity
import rmc.domain.repositories.AddressRepositoryInterface
import rmc.infrastructure.mappers.toAddressEntity
import rmc.infrastructure.tables.AddressTable

class AddressRepository : AddressRepositoryInterface {
    override fun findById(id: Int): AddressEntity? =
        transaction {
            AddressTable.selectAll().where { AddressTable.id eq id }
                .map { it.toAddressEntity() }
                .singleOrNull()
        }

    override fun save(address: AddressEntity): AddressEntity =
        transaction {
            val id =
                AddressTable.insert {
                    it[city] = address.city
                    it[street] = address.street
                    it[houseNumber] = address.houseNumber
                    it[subHouseNumber] = address.subHouseNumber
                    it[postalCode] = address.postalCode
                } get AddressTable.id

            address.copy(id = id)
        }
}
